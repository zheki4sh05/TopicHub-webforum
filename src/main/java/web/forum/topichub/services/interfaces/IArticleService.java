package web.forum.topichub.services.interfaces;

import web.forum.topichub.dto.*;

public interface IArticleService {
    Long create(ArticleDto articleDto, String id);
    PageResponse<ArticleDto> fetch(ArticleFilterDto articleFilter);
    void delete(String id, String userId);
    PageResponse<ArticleDto> search(SearchDto searchDto);
    PageResponse<ArticleDto> fetchBookMarks(String userId, Integer page);
    void deleteAdmin(String targetId);
    void update(ArticleDto updatedArticle, String id);
    void update(ArticleStatusDto articleStatusDto);

    ArticleDto findById(String id);
    String getStatusNameById(String id);

    void createArticle(Long articleId, String id);
}
