package web.forum.topichub.kafka;

import lombok.extern.slf4j.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Component
@Slf4j
public class CustomKafkaListener {
    @KafkaListener(topics = "image.saved", groupId = "group1")
    void listener(String data) {
        log.info("Received message [{}] in group1", data);
    }

    @KafkaListener(topics = "image.del" ,groupId = "group2")
    void listener2(String id){
        log.info("Received message [{}] in group2", id);
    }

}
