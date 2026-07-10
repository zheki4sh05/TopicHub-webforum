package web.forum.topichub.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CredentialsDto {
    private String code;
    private String email;
    private String newPass1;
    private String newPass2;
}
