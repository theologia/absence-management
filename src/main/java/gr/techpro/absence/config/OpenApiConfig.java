package gr.techpro.absence.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI absenceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Absence Management API")
                        .description("REST API for managing student absences across academic modules. " +
                                "Supports student/module CRUD, session scheduling, absence recording, " +
                                "justification workflow, and reporting.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("TechPro")
                                .email("info@techpro.gr"))
                        .license(new License()
                                .name("Academic Use Only")));
    }
}
