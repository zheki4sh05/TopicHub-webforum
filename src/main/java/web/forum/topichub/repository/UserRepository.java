package web.forum.topichub.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("""

select u 
from User u 
join fetch u.roles
where u.email= :data or u.login= :data
""")
    Optional<User> findByEmailOrLogin(@Param("data") String data);

    @Query("""

FROM User u where u.uuid != :id

""")
     List<User> findAll(@Param("id") String id);

    Page<User> findByStatus(String status, Pageable pageable);

    @Query("""

select u 
from User u 
join fetch u.roles
where u.email=:data or u.login=:data
""")
    User getByEmailOrLogin(@Param("data") String username);

    @Query("""
select u 
from User u 
where u.login like :login or u.email like :email
""")
    Page<User> searchByLoginOrEmail(@Param("login") String login, @Param("email") String email, Pageable pageable);
}
