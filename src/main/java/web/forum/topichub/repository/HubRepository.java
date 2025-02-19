package web.forum.topichub.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

@Repository
public interface HubRepository extends JpaRepository<Hub, Integer> {
}
