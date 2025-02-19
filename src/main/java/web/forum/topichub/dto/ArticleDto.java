package web.forum.topichub.dto;

import lombok.*;

import java.sql.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDto {

    private Long id;
    private String theme;
    private List<String> keyWords;
    private List<ArticlePartDto> list;
    private Integer likes;
    private Integer dislikes;
    private Timestamp created;

    public Integer getHub() {
        return hub==null ? 0 : hub;
    }

    private Integer hub;
    private UserDto userDto;
    private Integer likeState;
    private Integer commentsCount;

}
