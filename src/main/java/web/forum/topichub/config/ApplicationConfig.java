package web.forum.topichub.config;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.cache.*;
import org.springframework.cache.concurrent.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.client.*;
import web.forum.topichub.services.impls.*;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    @Bean
    public RestClient createRestClient(){
        return RestClient.create();
    }

    @Bean
    public CriteriaBuilder criteriaBuilder(EntityManager entityManager){
        return entityManager.getCriteriaBuilder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailsService();
//    }



    @Bean
    public RedisTemplate<Long, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }


}
