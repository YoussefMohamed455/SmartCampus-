package com.SmartCampus.org;

import org.springframework.context.annotation.Configuration;
    import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
    @Configuration
    @OpenAPIDefinition(
            info = @Info(title = "Smart Campus API", version = "1.0"),
            security = @SecurityRequirement(name = "bearerAuth") // 1. Tells Swagger to apply security globally
    )
    @SecurityScheme(
            name = "bearerAuth",          // 2. Matches the name above
            type = SecuritySchemeType.HTTP,
            scheme = "bearer",
            bearerFormat = "JWT"          // 3. Defines it as a JWT Bearer token
    )
    public class SwaggerConfig {
        // No code needed inside, the annotations do the work
    }
