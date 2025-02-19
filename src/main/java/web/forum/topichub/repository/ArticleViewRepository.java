package web.forum.topichub.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface ArticleViewRepository extends JpaRepository<Article, Long> {
    @Query("""

select a.status 
from Article a 
where a.id =:id

""")
    String getStatus(@Param("id") String id);


    @Query("""

select a 
from Article a 
where a.author.login like :author or a.theme like :theme or a.keyWords like :keywords
""")
    Page<Article> searchBy(@Param("author") String author,
                           @Param("theme") String theme,
                           @Param("keywords") String keywords,
                           Pageable pageable
    );

    @Query("""
select a 
from Article a 
join Bookmark b on b.article.id = a.id
where b.author.uuid = :userId
""")
    Page<Article> findBookmarks(@Param("userId") UUID userId, Pageable pageable);
}
