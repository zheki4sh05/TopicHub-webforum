package web.forum.topichub.controller.rest;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.services.interfaces.*;


/**
 * REST Controller for managing image retrieval.
 * Provides an endpoint for fetching an image associated with a user.
 *
 * <p>This controller handles the retrieval of images associated with a specific user by their user ID.
 * It interacts with the image service to fetch the image data and return it to the client.
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final IImageService imageService;

    /**
     * Retrieves the image for the specified user.
     *
     * <p>This endpoint allows users to retrieve the image associated with a specific user.
     * The user ID is provided as a request parameter, and the image data is returned as a byte array.
     *
     * @param userId the ID of the user whose image is to be fetched.
     * @return a ResponseEntity containing the image data as a resource and a 200 OK status.
     * @see IImageService#fetch(String)
     */
    @GetMapping("")
    public ResponseEntity<?> getLogo(
            @RequestParam("id") @NotNull String imageId
    ) {
        byte[] imageData = imageService.fetch(imageId);
        Resource resource = new ByteArrayResource(imageData);
        return new ResponseEntity<>(resource, HttpStatus.OK);

    }

//    @GetMapping("/article")
//    public ResponseEntity<?> getArticleImage(@RequestParam("id") @NotNull String imageId) {
//        byte[] imageData = imageService.fetch(imageId);
//        Resource resource = new ByteArrayResource(imageData);
//        return new ResponseEntity<>(resource, HttpStatus.OK);
//    }

}

