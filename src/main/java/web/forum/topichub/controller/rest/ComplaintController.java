package web.forum.topichub.controller.rest;


import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.services.interfaces.*;

/**
 * REST Controller for managing user complaints.
 * Provides endpoints for creating, retrieving, and deleting complaints.
 *
 * <p>This controller is responsible for managing user complaints, including creating new complaints,
 * retrieving complaints by name, and deleting complaints. It uses the complaint control service to handle
 * the business logic and the authentication service for user validation.
 *
 */
@RequestMapping("/api/v1/complaint")
@AllArgsConstructor
@RestController
public class ComplaintController {

    private final IComplaintControl complaintControl;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Creates a new complaint for the authenticated user.
     *
     * <p>This endpoint allows an authenticated user to create a new complaint. The complaint details should
     * be provided in the request body. The user ID is automatically retrieved using the custom security expression.
     *
     * @param complaintDto a request object containing the details of the complaint.
     * @return a ResponseEntity with a 201 Created status indicating the complaint was successfully created.
     * @see ComplaintDto
     */
    @PostMapping("")
    public ResponseEntity<?> doPost(@RequestBody ComplaintDto complaintDto)  {
        String userId = customSecurityExpression.getUserId();
        complaintDto.setUserDto(AuthorDto.builder()
                        .id(userId)
                .build());
        complaintControl.create(complaintDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deletes a complaint based on the complaint ID and name.
     *
     * <p>This endpoint allows the user to delete a specific complaint by providing the complaint ID and the name.
     *
     * @param complaintId the ID of the complaint to be deleted.
     * @param type the name of the complaint.
     * @return a ResponseEntity with the complaint ID and a 200 OK status indicating the complaint was successfully deleted.
     */
    @DeleteMapping("")
    public ResponseEntity<?> doDelete(
            @RequestParam("id") @NonNull String complaintId,
            @RequestParam("type") @NotNull String type
    ) {
        complaintControl.deleteById(complaintId, type);
        return new ResponseEntity<>(complaintId, HttpStatus.OK);
    }

}

