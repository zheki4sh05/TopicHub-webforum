package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface LikeRepository extends JpaRepository<Likes, UUID> {

        @Query("""
            From Likes l where l.user.uuid = :userId and l.article.id = :articleId
            """)
      Optional<Likes> findById(@Param("articleId") Long targetId, @Param("userId") UUID uuid);



    @Query("""

SELECT l.state FROM Likes l WHERE l.article.id = :articleId AND l.user.uuid = :userId

""")
    Integer userLikeState(@Param("userId") UUID userId,@Param("articleId") Long articleId);

}
