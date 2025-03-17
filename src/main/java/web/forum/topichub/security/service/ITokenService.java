package web.forum.topichub.security.service;

import web.forum.topichub.security.model.*;

import java.util.*;

public interface ITokenService {
    Token findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);
}
