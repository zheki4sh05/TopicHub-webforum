package web.forum.topichub.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDto {

    private String id;
    private String email;
    private String login;
    private String logoId;

}
