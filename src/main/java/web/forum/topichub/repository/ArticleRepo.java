package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {


    @Query("""

select ae 
from ArticleEntity  ae 
where ae.id= :articleId and ae.author.uuid = :authorId and (ae.status = :status or ae.status= :status2)

""")
    Optional<ArticleEntity> findSandboxByUser(@Param("authorId") UUID authorId,
                                              @Param("status") String status,
                                              @Param("status2") String status2,
                                              @Param("articleId") Long articleId
    );

    @Query("""

select ae 
from ArticleEntity ae 
where ae.id = :articleId and ae.author.uuid = :id

""")
    Optional<ArticleEntity> findByIdAndAuthor(Long articleId, UUID id);

    @Query("""

delete 
from ArticleEntity ae 
where ae.id = :aLong and ae.author.uuid = :userId

""")
    void deleteByIdAndArticleId(@Param("aLong") Long aLong,@Param("userId") UUID id);
}
