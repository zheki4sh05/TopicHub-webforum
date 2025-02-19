package web.forum.topichub.security.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import web.forum.topichub.security.model.*;

import java.util.*;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query("""
select t from Token t inner join User u on t.user.uuid = u.uuid
where t.user.uuid = :userId and t.loggedOut = false
""")
    List<Token> findAllAccessTokensByUser(@Param("userId") UUID userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token > findByRefreshToken(String token);
}
