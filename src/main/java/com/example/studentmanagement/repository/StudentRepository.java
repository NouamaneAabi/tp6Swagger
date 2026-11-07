package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    /**
     * Recherche un étudiant par son identifiant numérique.
     * Méthode dérivée de Spring Data JPA.
     */
    Student findById(int id);

    /**
     * Requête personnalisée en JPQL permettant d'agréger et compter
     * le nombre d'étudiants regroupés par année de naissance.
     * Chaque ligne retournée contient un tableau [Année (Integer), Nombre (Long)].
     */
    @Query("SELECT YEAR(s.dateNaissance), COUNT(s) FROM Student s GROUP BY YEAR(s.dateNaissance)")
    Collection<Object[]> findNbrStudentByYear();
}
