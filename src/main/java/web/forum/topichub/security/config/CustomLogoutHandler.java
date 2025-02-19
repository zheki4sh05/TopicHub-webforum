package web.forum.topichub.security.config;

import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.security.model.*;
import web.forum.topichub.security.repository.*;
import web.forum.topichub.security.util.*;

@Configuration
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader(Header.NAME.name());

        if(authHeader == null || !authHeader.startsWith(Header.ALIAS.name())) {
            return;
        }

        String token = authHeader.substring(Alias.LENGTH);
        Token storedToken = tokenRepository.findByAccessToken(token).orElse(null);
        var tokens = tokenRepository.findAllAccessTokensByUser(storedToken.getUser().getUuid());
        tokenRepository.deleteAll(tokens);

    }
}
