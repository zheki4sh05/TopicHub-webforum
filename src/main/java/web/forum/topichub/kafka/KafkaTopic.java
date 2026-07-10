package web.forum.topichub.kafka;

import org.apache.kafka.clients.admin.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;

@Configuration
public class KafkaTopic {

    @Value("${application.kafka.topic.emailSend}")
    private String topicName;

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("image.saved").build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("image.del").partitions(3).build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("email.send").build();
    }
}
