package web.forum.topichub.util;

import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import web.forum.topichub.security.dto.*;

@Component
public class HttpResponseUtils {

    @Value("${application.security.jwt.accessTokenExpire}")
    private Long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private Long refreshTokenExpire;

    @Value("${spring.application.name}")
    private String applicationName;

    public HttpResponseUtils(){}

    public HttpResponseUtils(Long accessTokenExpire, Long refreshTokenExpire, String applicationName) {
        this.accessTokenExpire = accessTokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
        this.applicationName = applicationName;
    }

    public String accessCookie(){
        return applicationName+"_access";
    }
    public String refreshCookie(){
        return applicationName+"_refresh";
    }

    public Cookie[] createCookie(AuthenticationResponse authenticationResponse) {
       Cookie[] cookies = new Cookie[2];
       cookies[0] = createTokenCookie(accessCookie(),authenticationResponse.getAccessToken(), accessTokenExpire);
        cookies[1] = createTokenCookie(refreshCookie(),authenticationResponse.getRefreshToken(), refreshTokenExpire);
        return cookies;
    }
    private Cookie createTokenCookie(String name, String value, Long expired){
        Cookie cookie = new Cookie(name,value);
        cookie.setMaxAge(expired.intValue());
        return cookie;
    }
}
