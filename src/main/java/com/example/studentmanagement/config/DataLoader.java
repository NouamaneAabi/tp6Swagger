package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner initDatabase(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                log.info("Base de données vide : initialisation avec des données de démonstration...");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // Conforme à l'exemple du TP (1985: 2 étudiants, 1990: 1 étudiant)
                repository.save(new Student("LACHGAR", "Mohamed", sdf.parse("1985-09-01")));
                repository.save(new Student("ALAMI", "Karim", sdf.parse("1985-04-15")));
                repository.save(new Student("BENANI", "Salma", sdf.parse("1990-11-20")));

                log.info("Données initiales insérées avec succès : 3 étudiants enregistrés.");
            } else {
                log.info("La base de données contient déjà {} étudiants. Pas d'insertion initiale.", repository.count());
            }
        };
    }
}
