package web.forum.topichub.repository;

import jakarta.persistence.*;
import org.springframework.stereotype.*;
import web.forum.topichub.model.*;

import java.util.*;

@Repository
public class UserDataRepository {

    @PersistenceContext
    private EntityManager em;

    public String getUserLogoId(String userId){
        return em.createQuery("SELECT u.logoId FROM User u WHERE u.uuid = :id", String.class)
                .setParameter("id", UUID.fromString(userId))
                .getSingleResult();
    }

}
