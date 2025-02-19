package web.forum.topichub.security.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import web.forum.topichub.dto.*;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("message")
    private String message;

    private UserDto userDto;
}
