package com.example.studentmanagement;

import com.example.studentmanagement.controller.StudentController;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    @DisplayName("Test 1: Enregistrement d'un étudiant avec retour 201 Created")
    void testSaveStudent() {
        Student student = new Student();
        student.setId(1);
        student.setNom("Mido");

        when(studentService.save(any(Student.class))).thenReturn(student);

        ResponseEntity<Student> response = studentController.save(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Mido", response.getBody().getNom());
        verify(studentService).save(any(Student.class));
    }

    @Test
    @DisplayName("Test 2: Suppression réussie d'un étudiant existant avec retour 204 No Content")
    void testDeleteStudent() {
        when(studentService.delete(1)).thenReturn(true);

        ResponseEntity<Void> response = studentController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService).delete(1);
    }

    @Test
    @DisplayName("Test 2 (Cas alternatif): Tentative de suppression d'un étudiant inexistant avec retour 404 Not Found")
    void testDeleteStudentNotFound() {
        when(studentService.delete(999)).thenReturn(false);

        ResponseEntity<Void> response = studentController.delete(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(studentService).delete(999);
    }

    @Test
    @DisplayName("Test 3: Récupération de tous les étudiants avec retour 200 OK")
    void testFindAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        when(studentService.findAll()).thenReturn(Arrays.asList(student1, student2));

        ResponseEntity<List<Student>> response = studentController.findAll();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService).findAll();
    }

    @Test
    @DisplayName("Test 4: Comptage total des étudiants avec retour 200 OK")
    void testCountStudents() {
        when(studentService.countStudents()).thenReturn(10L);

        ResponseEntity<Long> response = studentController.countStudent();

        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService).countStudents();
    }

    @Test
    @DisplayName("Test 5: Récupération de la répartition par année avec retour 200 OK")
    void testFindByYear() {
        when(studentService.findNbrStudentByYear()).thenReturn(Collections.emptyList());

        ResponseEntity<Collection<?>> response = studentController.findByYear();

        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(studentService).findNbrStudentByYear();
    }
}
