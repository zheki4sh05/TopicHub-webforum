package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import web.forum.topichub.model.*;

import java.util.*;

public interface BookmarkRepository extends JpaRepository<Bookmark, UUID> {

    @Query("""
            SELECT COUNT(b) > 0 FROM Bookmark b WHERE b.author.uuid = :authorId AND b.article.id = :articleId
       
            """)
     Boolean checkMarked(@Param("authorId") UUID userId, @Param("articleId") Long articleId);

    @Query(
            """
        select b 
        from Bookmark b
       where b.author.uuid = :userId and b.article.id = :articleId
"""
    )
    Optional<Bookmark> findByUserIdArticleId(@Param("userId") UUID userId,@Param("articleId") Long id);
}
