package web.forum.topichub.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
public class SwaggerUIConfig{

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.host}")
    private String hostName;

    private String createServerUrl(String hostName, String serverPort) {
        return "http://" + hostName + ":" + serverPort;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info().title("TopicHub REST API")
                        .version("1.0").contact(new Contact().name("Evgeniy Shostak").email("e.shostak05@gmail.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
