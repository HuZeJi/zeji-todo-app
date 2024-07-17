package com.huzeji.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi todoApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/public/api/**", "/secure/api/**")
                .build();
    }
}
