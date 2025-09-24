package cl.nexbyte.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

import java.util.List;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI api() {
    return new OpenAPI()
      .info(new Info()
        .title("Nexbyte v4 API")
        .version("0.0.1")
        .description("API pública de catálogo y endpoints internos de Nexbyte v4")
        .contact(new Contact().name("Nexbyte").email("soporte@nexbyte.cl")))
      .servers(List.of(new Server().url("http://localhost:8080").description("Local")))
      .components(new Components().addSecuritySchemes("bearer-jwt",
        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
      .group("public")
      .packagesToScan("cl.nexbyte.controller")
      .pathsToMatch("/api/**")
      .build();
  }
}
