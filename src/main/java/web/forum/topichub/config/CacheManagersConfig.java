package web.forum.topichub.config;

import org.springframework.cache.*;
import org.springframework.cache.annotation.*;
import org.springframework.cache.concurrent.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheManagersConfig {

    @Bean
    @Primary
    public CacheManager defaultCacheManager() {
        return new ConcurrentMapCacheManager("hubs");
    }

}
