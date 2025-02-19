package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import web.forum.topichub.model.*;

import java.util.*;


public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("""
From Comment c where c.article.id = :articleId
""")
    List<Comment> findAllByArticleId(@Param("articleId") Long aLong);

}
