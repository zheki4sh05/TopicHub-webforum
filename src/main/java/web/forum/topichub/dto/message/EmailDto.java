package web.forum.topichub.dto.message;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EmailDto {
    private String title;
    private String body;
    private String subject;
    private String to;
}
