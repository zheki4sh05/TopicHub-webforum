package web.forum.topichub.controller.rest;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;


/**
 * REST Controller for managing comments.
 * Provides endpoints for creating, updating, and deleting comments on articles.
 *
 * <p>This controller handles user operations related to comments, including posting new comments,
 * editing existing comments, and deleting comments. It uses the comment service to perform actions
 * on the comments and utilizes custom security expressions for user identification.
 *
 */
@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
public class CommentsController {

    private final ICommentsService commentsService;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Creates a new comment for an article.
     *
     * <p>This endpoint allows an authenticated user to post a new comment on an article. The comment information
     * should be provided in the request body. The user ID is automatically retrieved using the custom security
     * expression.
     *
     * @param commentDto a request object containing the comment data (content, article ID, etc.).
     * @return a ResponseEntity with a 201 Created status indicating the comment was successfully created.
     * @see CommentDto
     */
    @PostMapping("")
    public ResponseEntity<?> doPost(@RequestBody CommentDto commentDto) {
        String userId = customSecurityExpression.getUserId();
        CommentDto created = commentsService.create(commentDto, userId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Updates an existing comment.
     *
     * <p>This endpoint allows an authenticated user to update an existing comment. The updated comment data should
     * be provided in the request body. The user ID is automatically retrieved using the custom security expression.
     *
     * @param commentDto a request object containing the updated comment data.
     * @return a ResponseEntity with a 200 OK status indicating the comment was successfully updated.
     * @see CommentDto
     */
    @PutMapping("")
    public  ResponseEntity<?> doPut(@RequestBody CommentDto commentDto){
        String userId = customSecurityExpression.getUserId();
        CommentDto updated = commentsService.update(commentDto, userId);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }

    /**
     * Deletes an existing comment.
     *
     * <p>This endpoint allows an authenticated user to delete a comment. The comment to be deleted is specified
     * by the `commentId` request parameter. The user ID is automatically retrieved using the custom security expression.
     *
     * @param commentId the ID of the comment to be deleted.
     * @return a ResponseEntity with a 200 OK status indicating the comment was successfully deleted.
     */
    @DeleteMapping("")
    public  ResponseEntity<?> doDelete(@RequestParam("commentId") String commentId) {
        String userId = customSecurityExpression.getUserId();
        commentsService.delete(commentId, userId);
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }
}
