package web.forum.topichub.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDto {

    @NotNull
    private Long id;
    private String theme;
    private List<String> keyWords;
    private List<ArticlePartDto> list;
    private Integer likes;
    private Integer dislikes;
    private Timestamp created;
    private String previewId;
    public Integer getHub() {
        return hub==null ? 0 : hub;
    }

    private Integer hub;
    private UserDto userDto;
    private Integer likeState;
    private Integer commentsCount;

}
