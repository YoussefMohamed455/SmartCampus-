package com.SmartCampus.org.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Smart Campus API",
                version = "1.0",
                description = "Full API documentation for the Smart Campus System.",
                contact = @Contact(name = "Smart Campus Dev", email = "admin@smartcampus.com")
        )
)
public class OpenAPIConfig {  // <--- السطر ده كان ناقص عندك

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        // ده الرابط بتاعك https
        server.setUrl("https://just-presence-production-79e0.up.railway.app");
        server.setDescription("Railway Server (HTTPS)");

        return new OpenAPI().servers(List.of(server));
    }
}