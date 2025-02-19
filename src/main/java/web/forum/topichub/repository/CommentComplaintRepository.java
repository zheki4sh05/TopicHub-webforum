package web.forum.topichub.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import web.forum.topichub.model.complaints.*;

import java.util.*;

public interface CommentComplaintRepository extends JpaRepository<CommentComplaint, UUID> {

    @Query("""

From CommentComplaint ac where ac.comment.id = :article and ac.author.uuid = :author

""")
     Optional<CommentComplaint> findByCommentUserId(@Param("article") String targetId, @Param("author") UUID userId);

    @Query("""

From CommentComplaint  cc

""")
    Page<CommentComplaint> findAllComment(Pageable pageable);

    @Query("""

From CommentComplaint cc where cc.id= :id

""")
    Optional<CommentComplaint> findByIdComment(@Param("id") String complaintId);
}
