package ru.gorbunov.social_media_api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Social Media service API")
                        .description("This is a Spring Boot RESTful service for a simple social media API")
                        .termsOfService("terms")
                        .contact(new Contact().email("mihalich84@yandex.com"))
                        .version("2.0")
                );
    }
}
