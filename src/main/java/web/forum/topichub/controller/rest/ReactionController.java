package web.forum.topichub.controller.rest;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;

/**
 * REST Controller for managing user reactions to articles (likes, dislikes, etc.).
 * Provides endpoints for creating, deleting, and fetching reactions on articles.
 *
 * <p>This controller handles operations related to user reactions on articles. It allows users to add,
 * remove, and check their reactions (like/dislike) on articles.
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final IReactionService reactionService;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Creates a reaction (like/dislike) for an article.
     *
     * <p>This endpoint allows users to create a reaction (such as like or dislike) for an article.
     * The reaction is created based on the data provided in the request body, including the reaction name, value, and target article.
     *
     * @param reactionDto the DTO containing the reaction name, value, and target article ID.
     * @return a ResponseEntity containing the reaction data and a 201 CREATED status.
     * @see LikeRequestDto
     */
    @PostMapping("")
    public ResponseEntity<?> doPost(
            @RequestBody LikeRequestDto reactionDto
    ) {
        String userId = customSecurityExpression.getUserId();
        reactionService.makeReaction(reactionDto.getType(), Integer.valueOf(reactionDto.getValue()), userId, Long.valueOf(reactionDto.getTargetId()));
        return new ResponseEntity<>(reactionDto, HttpStatus.CREATED);
    }

    /**
     * Removes a reaction (like/dislike) for an article.
     *
     * <p>This endpoint allows users to remove a previously created reaction on an article.
     * The name of reaction and the target article ID are specified in the request parameters.
     *
     * @param type the name of reaction to remove (e.g., "like", "dislike").
     * @param targetId the ID of the article to remove the reaction from.
     * @return a ResponseEntity indicating the success of the removal with a 200 OK status.
     */
    @DeleteMapping("")
    public ResponseEntity<?> doDelete(
            @RequestParam("type") @NotNull String type,
            @RequestParam("targetId") @NotNull String targetId
    ) {
        String userId = customSecurityExpression.getUserId();
        reactionService.removeReaction(type, userId, Long.valueOf(targetId));
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * Checks the current user's reaction to a specific article.
     *
     * <p>This endpoint allows users to check their reaction (like/dislike) to a specific article written by a specific author.
     * It returns the current reaction data for the user on the specified article.
     *
     * @param articleId the ID of the article to check the reaction for.
     * @param authorId the ID of the article's author.
     * @return a ResponseEntity containing the reaction data and a 200 OK status.
     * @see ReactionDto
     */
    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam("articleId") @NotNull String articleId,
            @RequestParam("authorId") @NotNull String authorId
    ) {
        String userAuthId = customSecurityExpression.getUserId();
        ReactionDto reactionDto = reactionService.check(articleId, authorId, userAuthId);
        return new ResponseEntity<>(reactionDto, HttpStatus.OK);

    }
}

