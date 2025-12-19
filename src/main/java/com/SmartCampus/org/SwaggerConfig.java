package com.SmartCampus.org;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server; // <-- 1. Added this missing import

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "https://just-presence-production-79e0.up.railway.app", description = "Default Server URL")
        }, // <-- 2. Added this missing comma
        info = @Info(title = "Smart Campus API", version = "1.0"),
        security = { @SecurityRequirement(name = "bearerAuth") } // <-- 3. Added curly braces {}
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    // No code needed inside, the annotations do the work
}