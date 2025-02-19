package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {

}
