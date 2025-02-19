package web.forum.topichub.security.service;

import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.stereotype.*;

@Component
@AllArgsConstructor
public class SecurityService {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    public void check(String username, String token, HttpServletRequest httpServletRequest){

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

    }
}
