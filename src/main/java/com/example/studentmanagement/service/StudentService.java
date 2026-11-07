package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    /**
     * Injection des dépendances par constructeur (recommandée par l'équipe Spring).
     */
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Enregistre ou met à jour un étudiant.
     */
    public Student save(Student student) {
        log.info("Enregistrement de l'étudiant : {} {}", student.getPrenom(), student.getNom());
        return studentRepository.save(student);
    }

    /**
     * Supprime un étudiant à partir de son identifiant.
     * @return true si l'étudiant a été trouvé et supprimé, false sinon.
     */
    public boolean delete(int id) {
        log.info("Tentative de suppression de l'étudiant avec l'id : {}", id);
        Optional<Student> studentOptional = Optional.ofNullable(studentRepository.findById(id));
        if (studentOptional.isPresent()) {
            studentRepository.delete(studentOptional.get());
            log.info("Étudiant id {} supprimé avec succès.", id);
            return true;
        } else {
            log.warn("Suppression impossible : étudiant id {} introuvable.", id);
            return false;
        }
    }

    /**
     * Retourne la liste complète de tous les étudiants.
     */
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        log.info("Récupération de la liste complète des étudiants.");
        return studentRepository.findAll();
    }

    /**
     * Retourne le nombre total d'étudiants enregistrés en base.
     */
    @Transactional(readOnly = true)
    public long countStudents() {
        long count = studentRepository.count();
        log.info("Nombre total d'étudiants enregistrés : {}", count);
        return count;
    }

    /**
     * Récupère la répartition du nombre d'étudiants par année de naissance.
     */
    @Transactional(readOnly = true)
    public Collection<?> findNbrStudentByYear() {
        log.info("Calcul des statistiques d'étudiants par année de naissance.");
        return studentRepository.findNbrStudentByYear();
    }

    /**
     * Recherche d'un étudiant par son ID avec Optional.
     */
    @Transactional(readOnly = true)
    public Optional<Student> findById(int id) {
        return Optional.ofNullable(studentRepository.findById(id));
    }
}
