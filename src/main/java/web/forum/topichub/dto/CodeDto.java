package web.forum.topichub.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
public class CodeDto {
    private String email;
    private String code;
}
