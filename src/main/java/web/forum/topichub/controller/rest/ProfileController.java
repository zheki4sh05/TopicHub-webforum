package web.forum.topichub.controller.rest;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;


/**
 * REST Controller for managing user profiles.
 * Provides endpoints for retrieving, updating, deleting, and uploading user profile data.
 *
 * <p>This controller is responsible for handling operations related to user profiles, such as fetching user details,
 * updating user information, deleting articles, and uploading profile images.
 *
 * @author [Your Name]
 * @version 1.0
 * @since 2025-02-04
 */
@RequestMapping("/api/v1/profile")
@RestController
@AllArgsConstructor
public class ProfileController {
    private final IArticleService articleService;
    private final IAuthorService authService;
    private final IImageService imageService;
    private final CustomSecurityExpression customSecurityExpression;


    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "login",required = false,defaultValue="") String login,
            @RequestParam(value = "email",required = false,defaultValue="") String email,
            @RequestParam(value = "page",defaultValue = "1") Integer page
    ){
        if(login.isEmpty() && email.isEmpty()){
            throw new BadRequestException();
        }else{
            PageResponse<UserDto> authorDtoPageResponse = authService.search(login, email,page, false);
            return new ResponseEntity<>(authorDtoPageResponse, HttpStatus.OK);
        }
    }

    /**
     * Retrieves profile or articles based on the provided name and page.
     *
     * <p>This endpoint fetches either user articles or user profile information depending on the name parameter.
     * If the name is "articles", it returns a list of articles for the authenticated user.
     * If the name is "profile", it returns the user's profile information.
     *
     * @param type the name of data to retrieve: either "articles" or "profile".
     * @param page the page number for article pagination.
     * @return a ResponseEntity containing the requested data and a 200 OK status.
     * @see ArticleFilterDto
     * @see UserDto
     */
    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam("type")  String type,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page
    )  {
        String userId = customSecurityExpression.getUserId();
        switch (type) {
            case "articles" -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(articleService.fetch(createFilter(page, userId, userId)));
            }
            case "profile" -> {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(authService.findById(userId));
            }
            default -> {
                return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Deletes either an article or the user's profile.
     *
     * <p>This endpoint allows the user to delete either an article or their profile.
     * If the name is "article", the specified article is deleted.
     * If the name is "author", the user's profile is deleted.
     *
     * @param type the name of data to delete: either "article" or "author".
     * @param id the ID of the article or user to be deleted.
     * @return a ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("")
    public ResponseEntity<?> doDelete(
            @RequestParam("type") @NotNull String type,
            @RequestParam("id") String id
    ) {
        String userId = customSecurityExpression.getUserId();

        switch (type) {
            case "article" -> {
                articleService.delete(id, userId);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
            case "author" -> {
                authService.delete(userId);
                return new ResponseEntity<>(userId, HttpStatus.OK);
            }
            default -> {
                return new ResponseEntity<>("", HttpStatus.OK);
            }
        }
    }

    /**
     * Updates the user's profile information.
     *
     * <p>This endpoint allows the user to update their profile details by providing a UserDto object
     * containing the updated information.
     *
     * @param userDto the updated user profile information.
     * @return a ResponseEntity containing the updated user data and a 200 OK status.
     * @see UserDto
     */
    @PutMapping("")
    public ResponseEntity<?> doPut(
            @RequestBody UserDto userDto
    ) {
        String userId = customSecurityExpression.getUserId();
        authService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * Uploads a profile image for the user.
     *
     * <p>This endpoint allows the user to upload a new profile image. The image file is passed
     * as a multipart file in the request. The image is saved using the image service.
     *

     * @return a ResponseEntity indicating the result of the upload operation.
     */


    private ArticleFilterDto createFilter(Integer page,
                                          String user,
                                          String author){
        return  ArticleFilterDto.builder()
                .page(page)
                .userId(user)
                .authorId(author)
                .status(StatusDto.PUBLISH.name())
                .build();
    }

}


