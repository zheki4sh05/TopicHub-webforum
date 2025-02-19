package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.complaints.*;

import java.util.*;

@Repository
public interface ArticleComplaintRepository extends JpaRepository<ArticleComplaint, UUID> {


    @Query("""

From ArticleComplaint ac where ac.article.id = :article and ac.author.uuid = :author

""")
     Optional<ArticleComplaint> findByArticleUserId(@Param("article") Long targetId, @Param("author") UUID userId);


    @Query("""

From ArticleComplaint ac where ac.id= :id

""")
     Optional<ArticleComplaint> findByIdArticle(@Param("id") UUID complaintId);

}
