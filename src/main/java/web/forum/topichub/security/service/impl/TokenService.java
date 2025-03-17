package web.forum.topichub.security.service.impl;

import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.*;
import web.forum.topichub.security.model.*;
import web.forum.topichub.security.repository.*;
import web.forum.topichub.security.service.*;

import java.util.*;

@Service
@AllArgsConstructor
public class TokenService implements ITokenService {

    private final TokenRepository tokenRepository;

    @Override
    @Cacheable(cacheManager ="tokenCacheManager" , cacheNames = "tokens", key = "#token")
    public Token findByAccessToken(String token) {
        return  tokenRepository.findByAccessToken(token);
    }

    @Override
    public Optional<Token> findByRefreshToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }
}
