package web.forum.topichub.controller.rest;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.dto.client.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;

import java.io.*;

/**
 * REST Controller for managing sandbox articles.
 * Provides endpoints for creating and updating articles within a sandbox environment.
 *
 * <p>This controller allows users to create and update articles. It is primarily designed for a sandbox use case, where users can test article creation and updates.
 * The controller ensures that the user making the request has the correct user ID associated with the actions.
 *
 */
@RequestMapping("/api/v1/sandbox")
@AllArgsConstructor
@RestController
public class SandboxController {
    private final IArticleService articleService;
    private final CustomSecurityExpression customSecurityExpression;
    private final ISandboxService sandboxService;
    private final IImageService imageService;

    @PostMapping("")
    public ResponseEntity<?> doPost(
            @RequestBody ArticleDto newArticle
    ) {
        String id = customSecurityExpression.getUserId();
        articleService.publish(newArticle, id);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArticle(
            @RequestParam("articleId") Long articleId
    ){
        String id = customSecurityExpression.getUserId();
        articleService.createArticle(articleId, id);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> doPut(
            @RequestBody ArticleDto articleDto
    ) {
        String id = customSecurityExpression.getUserId();
        articleService.update(articleDto, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/template")
    public ResponseEntity<?> createTemplate(){
        String id = customSecurityExpression.getUserId();
       ArticleDto articleDto =  sandboxService.createSandbox(id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }

//    @GetMapping("/template")
//    public ResponseEntity<?> getTemplate(){
//        String id = customSecurityExpression.getUserId();
//        ArticleDto articleDto =  sandboxService.findSandbox(id);
//        return new ResponseEntity<>(articleDto, HttpStatus.OK);
//    }

    @DeleteMapping("/article")
    public  ResponseEntity<?> deleteArticle(
            @RequestParam("articleId") String articleId
    ){
        String id = customSecurityExpression.getUserId();
        sandboxService.deleteArticle(articleId,id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/template")
    public  ResponseEntity<?> deleteTemplate(
            @RequestParam("articleId") String articleId
    ){
        String id = customSecurityExpression.getUserId();
        sandboxService.clearSandbox(articleId,id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }


    @PostMapping("/part")
    public ResponseEntity<?> createPart(
            @RequestBody ArticlePartDto articlePartDto
    ){
        String id = customSecurityExpression.getUserId();
        ArticlePartDto articleDto =  sandboxService.createArticlePart(articlePartDto, id);
        return new ResponseEntity<>(articleDto, HttpStatus.OK);
    }
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(
            @RequestPart("file") MultipartFile multipartFile
            ){
        String id = customSecurityExpression.getUserId();
        String savedId = null;
        try {
            savedId = sandboxService.uploadImage(multipartFile,id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(savedId, HttpStatus.OK);
    }
    @DeleteMapping("/image")
    public ResponseEntity<?> deleteImage(
            @RequestParam("partId") String partId
    ){
        String id = customSecurityExpression.getUserId();

            partId = sandboxService.deleteImage(partId);

        return new ResponseEntity<>(partId, HttpStatus.OK);
    }

    @GetMapping("/image")
    public ResponseEntity<?> findById(
            @RequestParam("id") String imageId
    ){
        ImageDto imageDto = imageService.findById(imageId);
        return new ResponseEntity<>(imageDto,HttpStatus.OK);
    }


    @DeleteMapping("/part")
    public  ResponseEntity<?> deletePart(
            @RequestParam("articleId") String articleId,
            @RequestParam("partId") String partId
    ){
        String id = customSecurityExpression.getUserId();
        sandboxService.deletePart(articleId,partId,id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/preview")
    public ResponseEntity<?> createPreview(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestPart("name") String imageName
    ){
        String id = customSecurityExpression.getUserId();
        String imageId = null;
        try {
            imageId = sandboxService.createPreview(multipartFile,id,imageName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(imageId, HttpStatus.OK);
    }

    @DeleteMapping ("/preview")
    public ResponseEntity<?> deletePreview(
            @RequestParam("id") Long articleId
    ){
        String id = customSecurityExpression.getUserId();
        sandboxService.deletePreview(articleId,id);
        return new ResponseEntity<>(articleId, HttpStatus.OK);
    }


}

