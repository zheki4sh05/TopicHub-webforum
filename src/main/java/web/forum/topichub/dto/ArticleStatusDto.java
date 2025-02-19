package web.forum.topichub.dto;

import lombok.*;

import java.beans.*;

@Builder
@Getter
public class ArticleStatusDto {
    private String id;
    private String status;
    private Integer page;
    @ConstructorProperties({"id", "status", "page"})
    public ArticleStatusDto(String id, String status, Integer page) {
        this.id = id;
        this.status = status;
        this.page = page;
    }
}
