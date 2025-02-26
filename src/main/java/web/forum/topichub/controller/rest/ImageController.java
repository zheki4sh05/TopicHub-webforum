package web.forum.topichub.controller.rest;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;
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

    @GetMapping("")
    public ResponseEntity<?> getLogo(
            @RequestParam("id") @NotNull String imageId
    ) {
        byte[] imageData = imageService.fetch(imageId);
        Resource resource = new ByteArrayResource(imageData);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam("name") String value,
            @RequestParam(value = "page",defaultValue = "1",required = false) Integer page
    ){
        PageResponse<ImageDto> imageDtoPageResponse = imageService.search(value,page);
        return new ResponseEntity<>(imageDtoPageResponse, HttpStatus.OK);
    }

//    @GetMapping("/article")
//    public ResponseEntity<?> getArticleImage(@RequestParam("id") @NotNull String imageId) {
//        byte[] imageData = imageService.fetch(imageId);
//        Resource resource = new ByteArrayResource(imageData);
//        return new ResponseEntity<>(resource, HttpStatus.OK);
//    }

}

