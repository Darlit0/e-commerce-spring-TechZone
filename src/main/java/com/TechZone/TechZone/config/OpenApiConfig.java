package com.TechZone.TechZone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI techZoneOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TechZone API")
                        .description("API REST pour la boutique TechZone")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TechZone Team")
                                .email("contact@techzone.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/api/**")
                .build();
    }
}
