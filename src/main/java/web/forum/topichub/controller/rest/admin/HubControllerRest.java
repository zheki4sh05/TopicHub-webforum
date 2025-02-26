package web.forum.topichub.controller.rest.admin;


import jakarta.validation.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/hub")
public class HubControllerRest {

    private final IHubService hubService;

    @PostMapping("/create")
    public ResponseEntity<HubDto> createHub(
            @Valid @RequestBody HubDto hubDto
    ){
        return new ResponseEntity<>(hubService.create(hubDto), HttpStatus.OK);
    }
    @PatchMapping("/update")
    public ResponseEntity<HubDto> update(
            @Valid @RequestBody HubDto hubDto

    ){
        return new ResponseEntity<>(hubService.update(hubDto), HttpStatus.OK);
    }
    @DeleteMapping("")
    public ResponseEntity<String> delete(
            @RequestParam(value = "id") String id
    ){
        hubService.delete(Long.valueOf(id));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
