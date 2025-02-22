package web.forum.topichub.controller.rest;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import web.forum.topichub.dto.*;
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

    @PostMapping("")
    public ResponseEntity<?> doPost(
            @RequestBody ArticleDto newArticle
    ) {
        String id = customSecurityExpression.getUserId();
        articleService.create(newArticle, id);
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

    @PutMapping("")
    public ResponseEntity<?> doPut(
            @RequestBody ArticleDto articleDto
    ) {
        String id = customSecurityExpression.getUserId();
        articleService.update(articleDto, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/article")
    public ResponseEntity<?> createTemplate(
            @RequestBody ArticleDto articleDto
    ){
        String id = customSecurityExpression.getUserId();
       String savedId =  sandboxService.createSandbox(articleDto, id);
        return new ResponseEntity<>(savedId, HttpStatus.OK);
    }
    @DeleteMapping("/article")
    public  ResponseEntity<?> deleteArticle(
            @RequestParam("articleId") String articleId
    ){
        String id = customSecurityExpression.getUserId();
        sandboxService.deleteSandbox(articleId,id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }


    @PostMapping("/part")
    public ResponseEntity<?> createPart(
            @RequestBody ArticlePartDto articlePartDto
    ){
        String id = customSecurityExpression.getUserId();
        String savedId =  sandboxService.createArticlePart(articlePartDto, id);
        return new ResponseEntity<>(savedId, HttpStatus.OK);
    }
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(
            @RequestPart("file") MultipartFile multipartFile
            ){
        String id = customSecurityExpression.getUserId();
        String imageId = null;
        try {
            imageId = sandboxService.uploadImage(multipartFile,id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(imageId, HttpStatus.OK);
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

}

