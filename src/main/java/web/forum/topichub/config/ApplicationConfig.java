package web.forum.topichub.config;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.userdetails.*;
import web.forum.topichub.services.impls.*;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {

    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManager entityManager){
        return entityManager.getCriteriaBuilder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
