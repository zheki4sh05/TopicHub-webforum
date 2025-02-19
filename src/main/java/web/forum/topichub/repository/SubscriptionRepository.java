package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import web.forum.topichub.model.*;

import java.util.*;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query("""
SELECT COUNT(s) > 0 FROM Subscription s WHERE s.author.uuid = :authorId AND s.follower.uuid = :userId
""")
    Boolean checkSubscribe(@Param("userId") UUID userId, @Param("authorId") UUID authorId);

    @Query("""

From Subscription s where s.author.uuid= :authorId and s.follower.uuid = :follower

""")
    Optional<Subscription> findByUsers(@Param("authorId")UUID author, @Param("follower") UUID user);
    @Query("""
FROM Subscription s where s.author.uuid = :id
""")
    List<Subscription> findFollowersById(@Param("id") UUID id);

    @Query("""
FROM Subscription s where s.follower.uuid = :id
""")
    List<Subscription> fetch(@Param("id") UUID id);
}
