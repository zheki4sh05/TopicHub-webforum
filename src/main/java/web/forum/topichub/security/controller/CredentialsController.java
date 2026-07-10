package web.forum.topichub.security.controller;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.services.interfaces.*;

@RestController
@RequestMapping("api/v1/credentials")
@AllArgsConstructor
public class CredentialsController{

    private final ICredentialsService credentialsService;

    @PostMapping("/code")
    public ResponseEntity<Void> createCode(
            @RequestBody CodeDto request,
            WebRequest webRequest
    ) {
       credentialsService.generateCode(request,webRequest.getLocale());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/submit")
    public  ResponseEntity<?> updateCredentials(
            @RequestBody  CodeDto request
    ){
        String token = credentialsService.submitCode(request);
        return ResponseEntity.ok(token);
    }

    @PatchMapping("/update")
    public  ResponseEntity<Void> updateCredentials(
            @RequestBody  CredentialsDto credentialsDto
    ){
        credentialsService.update(credentialsDto);
        return ResponseEntity.ok().build();
    }

}
