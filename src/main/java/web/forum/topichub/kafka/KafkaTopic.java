package web.forum.topichub.kafka;

import org.apache.kafka.clients.admin.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;

@Configuration
public class KafkaTopic {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("topic-1").build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("topic-2").partitions(3).build();
    }
}
