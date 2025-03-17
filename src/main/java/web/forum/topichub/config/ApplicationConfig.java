package web.forum.topichub.config;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.cache.*;
import org.springframework.cache.concurrent.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.*;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.*;
import org.springframework.data.redis.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.client.*;
import web.forum.topichub.services.impls.*;

import java.time.*;

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
    public JedisConnectionFactory jedisConnectionFactory()
    {

        // redis server properties we write here if we are in same machine than there is no need to write properties

        // jedisConnectionFactory.setHostName("localhost");
        // jedisConnectionFactory.setPort(6379);

        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<Long, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10));
        return RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(cacheConfiguration)
                .withCacheConfiguration("userDetails", cacheConfiguration)
                .build();
    }

    @Bean
    public CacheManager tokenCacheManager() {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30));
        return RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(cacheConfiguration)
                .withCacheConfiguration("tokens", cacheConfiguration)
                .build();
    }



}
