package web.forum.topichub.dto;

import lombok.*;
import web.forum.topichub.dto.filter.*;
import web.forum.topichub.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleFilterDto implements IBusinessLogicFilterSupplier, IFactoryFilterDataSupplier {
    private String month;
    private String year;
    private String rating;
    private Integer page;

    private Integer size = PageSize.SIZE;

    public String getStatus() {
        return status;
    }

    private String status;
    private String userId;
    private Integer param;
    private Integer hub;
    private String authorId;
    private String type;

    @Override
    public Integer getParam() {
        return hub;
    }

    @Override
    public String getAuthorId() {
        return authorId;
    }

    @Override
    public Integer getMonth() {
        return month==null ? null: Integer.parseInt(month);
    }

    @Override
    public Integer getYear() {
        return year==null ? null: Integer.parseInt(year);
    }
    public Integer size() {
        return size;
    }

    public String getRating() {
        return rating;
    }

    public String getUserId() {
        return userId;
    }

    public int getPage() {
        return page;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
