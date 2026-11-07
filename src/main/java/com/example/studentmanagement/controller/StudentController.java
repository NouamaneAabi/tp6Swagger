package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
@Tag(name = "Gestion des Étudiants", description = "Endpoints de gestion CRUD et analyses statistiques des étudiants")
public class StudentController {

    @Autowired
    private StudentService studentService;

    public StudentController() {
    }

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/save")
    @Operation(
            summary = "Ajouter ou modifier un étudiant",
            description = "Enregistre un nouvel étudiant dans la base de données. Si un identifiant existant est fourni, l'étudiant est mis à jour."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Étudiant enregistré avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données d'entrée invalides (erreur de validation)",
                    content = @Content
            )
    })
    public ResponseEntity<Student> save(
            @Parameter(description = "Objet Student à enregistrer", required = true)
            @Valid @RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Supprimer un étudiant par son identifiant",
            description = "Supprime définitivement un étudiant de la base de données s'il existe."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès (aucun contenu renvoyé)"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable pour l'identifiant spécifié")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant numérique unique de l'étudiant à supprimer", required = true, example = "1")
            @PathVariable("id") int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Récupérer la liste complète des étudiants",
            description = "Retourne tous les enregistrements d'étudiants présents dans la base de données."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des étudiants récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Student.class))
                    )
            )
    })
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(
            summary = "Obtenir le nombre total d'étudiants",
            description = "Effectue un comptage de tous les étudiants enregistrés dans la table."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Total calculé avec succès",
                    content = @Content(mediaType = "application/json", schema = @Schema(type = "integer", example = "42"))
            )
    })
    public ResponseEntity<Long> countStudent() {
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/byYear")
    @Operation(
            summary = "Répartition des étudiants par année de naissance",
            description = "Calcule et agrège le nombre d'étudiants par année de naissance à l'aide d'une requête JPQL groupée."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Statistiques par année renvoyées avec succès (ex: [[1985, 2], [1990, 1]])",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<Collection<?>> findByYear() {
        Collection<?> studentsByYear = studentService.findNbrStudentByYear();
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK);
    }
}
