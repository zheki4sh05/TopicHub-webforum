package web.forum.topichub.kafka;

import org.apache.kafka.clients.admin.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("image.saved").build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("image.del").partitions(3).build();
    }
}
