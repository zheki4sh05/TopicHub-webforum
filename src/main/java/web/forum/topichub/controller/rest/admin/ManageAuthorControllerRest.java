package web.forum.topichub.controller.rest.admin;


import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.services.interfaces.*;
import web.forum.topichub.util.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/author")
public class ManageAuthorControllerRest {

    private final IAuthorService authService;

    @GetMapping("/fetch")
    public ResponseEntity< PageResponse<UserDto>> getAuthor(
            @RequestParam(value = "status", defaultValue = "ACTIVE") String status,
            @RequestParam(value = "page", defaultValue = "1") Integer number,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        if(HttpRequestUtils.contains(status)){
            var pageable = PageRequest.of(number==0 ? number : number -1, size);
            var userListPage = authService.fetch(status, pageable);
            return new ResponseEntity<>(userListPage, HttpStatus.OK);
        }else{
            throw new BadRequestException();

        }
    }
    @PostMapping("/delete")
    public  ResponseEntity<String> deleteAuthor(
            @RequestParam("id") String id
    ){
        authService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<?> manageStatus(
            @RequestParam("id") String id,
            @RequestParam(value = "status") String status
    ){
        if(HttpRequestUtils.contains(status)){
            authService.manageBlock(id, status);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }else{
           return new ResponseEntity<>(StatusDto.values(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(value = "login",required = false,defaultValue="") String login,
            @RequestParam(value = "email",required = false,defaultValue="") String email,
            @RequestParam(value = "page",defaultValue = "1") Integer page
    ){
        if(login.isEmpty() && email.isEmpty()){
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }else{
            PageResponse<UserDto> authorDtoPageResponse = authService.search(login, email,page,true);
            return new ResponseEntity<>(authorDtoPageResponse, HttpStatus.OK);
        }
    }

}
