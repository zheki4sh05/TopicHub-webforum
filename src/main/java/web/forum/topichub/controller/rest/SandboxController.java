package web.forum.topichub.controller.rest;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;

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

    /**
     * Creates a new article in the sandbox.
     *
     * <p>This endpoint allows users to create a new article in the sandbox. It takes an article DTO as input,
     * associates it with the current user's ID, and calls the service to create the article.
     *
     * @param newArticle the DTO containing the article's details to be created.
     * @return a ResponseEntity with a 201 CREATED status.
     * @see ArticleDto
     */
    @PostMapping("")
    public ResponseEntity<?> doPost(
            @RequestBody ArticleDto newArticle
    ) {
        String id = customSecurityExpression.getUserId();
        articleService.create(newArticle, id);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    /**
     * Updates an existing article in the sandbox.
     *
     * <p>This endpoint allows users to update an article in the sandbox. It takes an article DTO as input,
     * associates it with the current user's ID, and calls the service to update the article.
     *
     * @param articleDto the DTO containing the updated article details.
     * @return a ResponseEntity with a 200 OK status.
     * @see ArticleDto
     */
    @PutMapping("")
    public ResponseEntity<?> doPut(
            @RequestBody ArticleDto articleDto
    ) {
        String id = customSecurityExpression.getUserId();
        articleService.update(articleDto, id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}

