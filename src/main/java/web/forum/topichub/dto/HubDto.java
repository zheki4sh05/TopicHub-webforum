package web.forum.topichub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HubDto {
    private String id;
    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    private String en;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 10)
    private String ru;
}
