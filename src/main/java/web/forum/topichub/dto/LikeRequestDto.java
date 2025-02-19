package web.forum.topichub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LikeRequestDto {

    @NotEmpty
    private String type;

    @NotEmpty
    private String value;

    @NotEmpty
    private  String targetId;



}


