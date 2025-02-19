package web.forum.topichub.controller.rest;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;

import java.util.*;

/**
 * REST Controller for managing user subscriptions and followers.
 * Provides endpoints for subscribing to authors, managing subscriptions, and fetching subscribers/followers.
 *
 * <p>This controller allows users to subscribe to authors, unsubscribe, and retrieve the list of subscriptions or followers.
 * It uses the current user's ID to perform subscription actions.
 *
 */
@RequestMapping("/api/v1/subscription")
@AllArgsConstructor
@RestController
public class SubscribeController {

    private final IReactionService reactionService;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Fetches a list of authors that the user has subscribed to or the list of followers.
     *
     * <p>This endpoint allows users to fetch either a list of authors they have subscribed to or a list of followers,
     * based on the name specified in the request.
     *
     * @param type the name of subscription to fetch: either "subscribes" for subscribed authors or "followers" for followers.
     * @return a ResponseEntity containing a list of AuthorDto objects and a 200 OK status if successful, or a 400 BAD REQUEST if the name is invalid.
     * @see AuthorDto
     */
    @GetMapping("")
    public ResponseEntity<?> doGet(
            @RequestParam("type") @NotNull String type
    ) {
        String id = customSecurityExpression.getUserId();
        switch(type){
            case "subscribes" -> {
                List<AuthorDto> authorDtos = reactionService.fetchAllSubscribes(id);
                return new ResponseEntity<>(authorDtos, HttpStatus.OK);
            }
            case "followers" -> {
                List<AuthorDto> authorDtos = reactionService.fetchAllFollowers(id);
                return new ResponseEntity<>(authorDtos, HttpStatus.OK);
            }
            default -> {
                return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * Subscribes the user to an author.
     *
     * <p>This endpoint allows users to subscribe to an author. It requires a SubscriptionRequestDto containing the author ID to subscribe to.
     *
     * @param subscriptionRequestDto the DTO containing the author's ID to subscribe to.
     * @return a ResponseEntity with a 201 CREATED status if the subscription was successfully created.
     * @see SubscriptionRequestDto
     */
    @PostMapping("")
    public ResponseEntity<?> doPost(
            @RequestBody SubscriptionRequestDto subscriptionRequestDto
    ) {
        String userId = customSecurityExpression.getUserId();
        reactionService.manageSubscription(1, subscriptionRequestDto.getAuthor(), userId);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

    /**
     * Unsubscribes the user from an author.
     *
     * <p>This endpoint allows users to unsubscribe from an author.
     *
     * @param author the ID of the author to unsubscribe from.
     * @return a ResponseEntity with a 200 OK status if the unsubscription was successful.
     */
    @DeleteMapping("")
    public ResponseEntity<?> doDelete(
            @RequestParam("author") @NotNull String author
    ) {
        String userId = customSecurityExpression.getUserId();
        reactionService.manageSubscription(-1, author, userId);
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
}
