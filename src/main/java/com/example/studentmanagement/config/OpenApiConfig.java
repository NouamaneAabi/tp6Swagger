package com.example.studentmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentManagementOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Serveur Local de Développement");

        Contact contact = new Contact()
                .name("Département Génie Informatique (5IIR)")
                .email("contact@studentmanagement.edu");

        License mitLicense = new License()
                .name("Licence Académique & Pédagogique")
                .url("https://springdoc.org");

        Info info = new Info()
                .title("API de Gestion des Étudiants (Student Management API)")
                .version("1.0.0")
                .contact(contact)
                .description("""
                        Cette API RESTful permet d'administrer le cycle de vie des dossiers étudiants :
                        * Enregistrement et mise à jour d'un étudiant (`POST /students/save`)
                        * Consultation de l'ensemble des étudiants (`GET /students/all`)
                        * Suppression ciblée d'un étudiant (`DELETE /students/delete/{id}`)
                        * Comptage global des effectifs (`GET /students/count`)
                        * Statistiques d'effectifs agrégées par année de naissance (`GET /students/byYear`)
                        """)
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
