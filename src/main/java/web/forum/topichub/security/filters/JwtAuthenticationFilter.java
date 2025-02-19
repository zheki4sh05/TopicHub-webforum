package web.forum.topichub.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.service.*;
import web.forum.topichub.security.util.*;
import web.forum.topichub.util.*;

import java.io.*;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final HttpRequestUtils httpRequestUtils;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(Header.NAME.type());

        if (httpRequestUtils.isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authHeader == null || !authHeader.startsWith(Header.ALIAS.type())) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(Alias.LENGTH);
        String username = jwtService.extractUsername(token);
        securityService.check(username, token, request);
        filterChain.doFilter(request, response);
    }
}
