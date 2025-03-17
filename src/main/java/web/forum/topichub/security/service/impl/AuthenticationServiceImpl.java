package web.forum.topichub.security.service.impl;

import jakarta.servlet.http.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import web.forum.topichub.dto.*;
import web.forum.topichub.exceptions.*;
import web.forum.topichub.mapper.*;
import web.forum.topichub.model.*;
import web.forum.topichub.repository.*;
import web.forum.topichub.security.dto.*;
import web.forum.topichub.security.model.*;
import web.forum.topichub.security.repository.*;
import web.forum.topichub.security.service.*;
import web.forum.topichub.util.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserRoleRepository userRoleRepository;
    @Transactional
    public AuthenticationResponse register(SignUpDto authDto) {


        Optional<User> isExist = repository.findByEmailOrLogin(authDto.getLogin());
        if(isExist.isPresent()) {
            throw new EntityAlreadyExists(ErrorKey.CONFLICT.key());
        }
        var user  = userMapper.mapFrom(authDto);
        user = repository.save(user);
        var role = userRoleMapper.mapFrom(user);
        userRoleRepository.save(role);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful",null);

    }

    @Transactional
    public AuthenticationResponse authenticate(AuthDto request) {

        User user = repository.getByEmailOrLogin(request.getLogin());
        UserDto userDto = checkUser(user, request);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);
        return new AuthenticationResponse
                (accessToken, refreshToken, "User login was successful", userDto);

    }
    private UserDto checkUser(User isExist, AuthDto userDto) {
        if (isExist == null) {
            throw new InvalidCredentialsException(ErrorKey.NOT_FOUND.key());
        } else if (isExist.getStatus().equals(StatusDto.BLOCK.name())) {
            throw new UserBlockException();
        } else {
            if (new PasswordEncoderWrapper().matches(userDto.getPassword(), isExist.getPassword())) {
                return userMapper.toDto(isExist);
            } else {
                throw new InvalidCredentialsException(ErrorKey.CREDENTIALS.key());
            }
        }
    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUuid());
        if(validTokens.isEmpty()) {
            return;
        }
        tokenRepository.deleteAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setId(UUID.randomUUID());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    @Transactional
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        String username = jwtService.extractUsername(token);

        User user = repository.findByEmailOrLogin(username)
                .orElseThrow(EntityNotFoundException::new);

        if(jwtService.isValidRefreshToken(token, user)) {

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);
            UserDto userDto = userMapper.toDto(user);
            return new ResponseEntity<>(new AuthenticationResponse(accessToken, refreshToken, "New token generated",userDto), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
