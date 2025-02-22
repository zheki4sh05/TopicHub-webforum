package web.forum.topichub.kafka;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.*;

@Component
@Slf4j
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topicName) {
        log.info("Sending : {}", message);
        log.info("--------------------------------");
        kafkaTemplate.send(topicName, message);
    }
}
