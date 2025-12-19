package com.SmartCampus.org.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@OpenAPIDefinition(
public OpenAPI customOpenAPI() {
    Server server = new Server();
    // ⚠️ حط رابط مشروعك هنا بالظبط زي ما هو مكتوب
    server.setUrl("https://just-presence-production-79e0.up.railway.app");
    server.setDescription("Railway Server (HTTPS)");

    return new OpenAPI().servers(List.of(server));
}
@OpenAPIDefinition(
        info = @Info(
                title = "Smart Campus API",
                version = "1.0",
                description = "Full API documentation for the Smart Campus System.",
                contact = @Contact(name = "Smart Campus Dev", email = "admin@smartcampus.com")
        ),
        // This applies security globally to all endpoints
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenAPIConfig {
    // No bean needed here, the annotations do the magic!
}