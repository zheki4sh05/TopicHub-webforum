package web.forum.topichub.dto;

import lombok.*;

import java.util.*;

@Builder
@Getter
public class PageDto<T> {
    private List<T> content;
    private Long total;
    private Integer pageNumber;
    private Integer lastPage;

    @Override
    public String toString() {
        return "PageDto{" +
                "content size=" + content.size() +
                ", total=" + total +
                ", pageNumber=" + pageNumber +
                ", lastPage=" + lastPage +
                '}';
    }
}
