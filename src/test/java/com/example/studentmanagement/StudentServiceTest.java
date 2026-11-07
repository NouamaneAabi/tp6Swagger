package com.example.studentmanagement;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1, "LACHGAR", "Mohamed", new Date());
    }

    @Test
    @DisplayName("Service: Enregistrement d'un étudiant avec persistance")
    void testSave() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student saved = studentService.save(student);

        assertNotNull(saved);
        assertEquals("LACHGAR", saved.getNom());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Service: Suppression réussie quand l'étudiant existe")
    void testDeleteWhenStudentExists() {
        when(studentRepository.findById(1)).thenReturn(student);
        doNothing().when(studentRepository).delete(student);

        boolean result = studentService.delete(1);

        assertTrue(result);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    @DisplayName("Service: Suppression échouée quand l'étudiant n'existe pas")
    void testDeleteWhenStudentNotFound() {
        when(studentRepository.findById(99)).thenReturn(null);

        boolean result = studentService.delete(99);

        assertFalse(result);
        verify(studentRepository, never()).delete(any(Student.class));
    }

    @Test
    @DisplayName("Service: Recherche de tous les étudiants")
    void testFindAll() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> list = studentService.findAll();

        assertEquals(1, list.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Service: Comptage du nombre d'étudiants")
    void testCountStudents() {
        when(studentRepository.count()).thenReturn(5L);

        long count = studentService.countStudents();

        assertEquals(5L, count);
        verify(studentRepository, times(1)).count();
    }

    @Test
    @DisplayName("Service: Calcul des effectifs par année")
    void testFindNbrStudentByYear() {
        Collection<Object[]> stats = List.of(new Object[]{1985, 2L}, new Object[]{1990, 1L});
        when(studentRepository.findNbrStudentByYear()).thenReturn(stats);

        Collection<?> result = studentService.findNbrStudentByYear();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findNbrStudentByYear();
    }
}
