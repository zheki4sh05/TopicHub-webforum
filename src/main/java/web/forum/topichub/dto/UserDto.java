package web.forum.topichub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class UserDto {
    private String id;

    @NotBlank(message ="LOGIN_EMPTY")
    private String login;

    @Email(message = "EMAIL_INCORRECT")
    private String email;

    @NotBlank(message = "PASS_EMPTY")
    @NotEmpty(message = "PASS_EMPTY")
    @Size( min = 6,message = "PASS_INCORRECT", max = 12)
    private String password;

    private List<String> roles;

    private Boolean state;

    private String  status;
    private String logoId;
}
