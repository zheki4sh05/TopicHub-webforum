package web.forum.topichub.security.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.filter.*;
import web.forum.topichub.security.service.*;
import web.forum.topichub.util.*;

import java.io.*;

@Component
@RequiredArgsConstructor
public class JwtCookieAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final HttpResponseUtils httpResponseUtils;
    private final SecurityService securityService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {


        if (!request.getServletPath().contains("admin")) {
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getCookies()!=null){
            String accessToken=null;
            String refreshToken=null;
            var cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (httpResponseUtils.accessCookie().equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
                if (httpResponseUtils.refreshCookie().equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
            if(accessToken!=null && refreshToken!=null){
                String username = jwtService.extractUsername(accessToken);
                securityService.check(username, accessToken, request);
                filterChain.doFilter(request, response);
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

}
