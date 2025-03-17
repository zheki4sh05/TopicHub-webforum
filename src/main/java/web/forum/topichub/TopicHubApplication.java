package web.forum.topichub;

import de.codecentric.boot.admin.server.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.*;

@SpringBootApplication
@EnableCaching
public class TopicHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopicHubApplication.class, args);
	}

}
