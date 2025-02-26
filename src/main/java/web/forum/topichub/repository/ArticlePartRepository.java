package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface ArticlePartRepository extends JpaRepository<ArticlePart, UUID> {


    @Query("""

select ap 
from ArticlePart ap 
join ArticleEntity ae on ae.id = :articleId and ae.author.uuid = :id
where ap.uuid = :partId

""")
    ArticlePart findByAuthorArticleAndId(@Param("id") UUID id,
                                         @Param("articleId") Long articleId,
                                         @Param("partId") UUID partId);

    @Modifying
    @Query("""
DELETE from ArticlePart ap where ap.article.id= :articleId

""")
    void deleteByArticleId(@Param("articleId") Long id);

    @Modifying
    @Query("""

delete from ArticlePart  ap where ap.article.id= :articleId and ap.uuid= :partId and ap.article.author.uuid= :userId

""")
    void deleteByIdAndArticle(@Param("partId") UUID partId, @Param("articleId") String articleId,@Param("userId") UUID id);
}
