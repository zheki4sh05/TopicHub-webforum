package web.forum.topichub.controller.rest.admin;

import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/complaints")
public class ComplaintsController {

    private final IComplaintControl complaintControl;

    @GetMapping("/fetch/{type}")
    public ResponseEntity<PageResponse<ComplaintDto>> getComplaints(
            @PathVariable("type") String type,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        var pageable = PageRequest.of(page==0 ? page : page -1, size);
        var complaintDtos =  complaintControl.findAllByType(type, pageable);
        return new ResponseEntity<>(complaintDtos, HttpStatus.OK);
    }

    @DeleteMapping ("/del/{type}")
    public ResponseEntity<?> delComplaint(
            @PathVariable("type") String type,
            @RequestParam(value = "id") String id
    ){
        complaintControl.deleteById(id,type);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
