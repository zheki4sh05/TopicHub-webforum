package web.forum.topichub.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SearchDto{
    private String theme;
    private String keywords;
    private String author;
    private String userId;
    private ArticleFilterDto articleFilterDto;
    public SearchDto() {
    }
    
}
