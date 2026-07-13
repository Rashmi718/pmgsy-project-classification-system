package com.pmgsy.classifier.config;

import com.pmgsy.classifier.config.properties.SwaggerProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition
public class OpenApiConfig {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("pmgsy-public")
                .pathsToMatch("/**")
            .addOpenApiCustomizer(openApi -> openApi.setInfo(new Info()
                .title(swaggerProperties.title())
                .description(swaggerProperties.description())
                .version(swaggerProperties.version())))
                .build();
    }
}