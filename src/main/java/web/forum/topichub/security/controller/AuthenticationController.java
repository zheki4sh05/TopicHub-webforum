package web.forum.topichub.security.controller;

import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import web.forum.topichub.security.dto.*;
import web.forum.topichub.security.service.impl.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authService;


    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestBody SignUpDto request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthDto request
    ) {
       var response =  authService.authenticate(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }
}
