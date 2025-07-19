
package thelazycoder.school_expenditure_management.Configuration;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("EatNow API")
                        .description("EatNow is a platform for users to order food from various vendors.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Team EatNow")
                                .email("eatnow.ng@gmail.com")
                        )
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("EatNow Documentation")
                        .url("https://github.com/Hackthejobs-Eatnow/eatnow_backend")
                )
                .servers(List.of(
                        new Server().url("http://localhost:8085").description("Localhost"),
                        new Server().url("server2").description("Railway")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}
