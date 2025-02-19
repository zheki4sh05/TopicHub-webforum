package web.forum.topichub.controller.rest;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;

import java.util.*;

/**
 * REST Controller for managing hubs.
 * Provides an endpoint for retrieving a list of all hubs.
 *
 * <p>This controller handles requests related to hubs, such as retrieving all available hubs.
 * It interacts with the hub service to fetch the hub data.
 *
 */
@RestController
@RequestMapping("api/v1/hubs")
@AllArgsConstructor
public class HubsController {

    private final IHubService hubService;

    /**
     * Retrieves a list of all hubs.
     *
     * <p>This endpoint allows users to retrieve all available hubs from the system.
     * It fetches the hubs using the hub service and returns them in the response.
     *
     * @return a ResponseEntity containing a list of hubs and a 200 OK status.
     * @see HubDto
     */
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<HubDto> hubDtoList = hubService.findAll();
        return new ResponseEntity<>(hubDtoList, HttpStatus.OK);
    }
}

