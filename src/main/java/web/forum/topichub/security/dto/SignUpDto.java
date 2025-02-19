package web.forum.topichub.security.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String login;
    private String email;
    private String password;
}
