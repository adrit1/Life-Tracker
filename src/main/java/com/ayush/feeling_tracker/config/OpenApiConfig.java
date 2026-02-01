package com.ayush.feeling_tracker.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Feeling Tracker API",
        version = "1.0",
        description = "API for tracking hourly feelings"
    )
)
public class OpenApiConfig {
}
