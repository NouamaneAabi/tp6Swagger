package com.example.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "student")
@Schema(description = "Entité représentant un étudiant au sein du système universitaire")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant auto-généré de l'étudiant", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @NotBlank(message = "Le nom de famille est obligatoire")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nom de famille de l'étudiant", example = "LACHGAR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false, length = 100)
    @Schema(description = "Prénom de l'étudiant", example = "Mohamed", requiredMode = Schema.RequiredMode.REQUIRED)
    private String prenom;

    @Past(message = "La date de naissance doit être située dans le passé")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date_naissance")
    @Schema(description = "Date de naissance au format YYYY-MM-DD", example = "1985-09-01", type = "string", format = "date")
    private Date dateNaissance;

    // Constructeur sans argument (requis par JPA)
    public Student() {
    }

    // Constructeur pratique pour initialiser un étudiant sans id (pour la création)
    public Student(String nom, String prenom, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // Constructeur complet
    public Student(int id, String nom, String prenom, Date dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(nom, student.nom) &&
                Objects.equals(prenom, student.prenom) &&
                Objects.equals(dateNaissance, student.dateNaissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, dateNaissance);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }
}
