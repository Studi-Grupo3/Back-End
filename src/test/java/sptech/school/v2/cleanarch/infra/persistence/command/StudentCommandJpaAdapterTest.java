package sptech.school.v2.cleanarch.infra.persistence.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.Responsible;
import sptech.school.v2.cleanarch.infra.persistence.repository.StudentJpaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("StudentCommandJpaAdapter Tests")
@ExtendWith(MockitoExtension.class)
class StudentCommandJpaAdapterTest {

    private StudentCommandJpaAdapter studentCommandJpaAdapter;

    @Mock
    private StudentJpaRepository studentJpaRepository;

    @BeforeEach
    void setUp() {
        studentCommandJpaAdapter = new StudentCommandJpaAdapter(studentJpaRepository);
    }

    @Test
    @DisplayName("Should save a student")
    void testSaveStudent() {
        Student student = new Student("João Silva", "joao@email.com", "12345678901", "password");
        student.setId(1);
        student.setSchoolGrade("10º ano");
        student.setSchoolName("Escola Municipal");

        when(studentJpaRepository.save(student)).thenReturn(student);

        Student result = studentCommandJpaAdapter.save(student);

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        assertEquals("joao@email.com", result.getEmail());
        verify(studentJpaRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Should update a student")
    void testUpdateStudent() {
        Student student = new Student("Maria Santos", "maria@email.com", "98765432101", "password");
        student.setId(2);
        student.setSchoolGrade("11º ano");

        when(studentJpaRepository.save(student)).thenReturn(student);

        Student result = studentCommandJpaAdapter.update(student);

        assertNotNull(result);
        assertEquals("Maria Santos", result.getName());
        verify(studentJpaRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Should delete a student by ID")
    void testDeleteStudent() {
        Integer studentId = 1;

        studentCommandJpaAdapter.delete(studentId);

        verify(studentJpaRepository, times(1)).deleteById(studentId);
    }

    @Test
    @DisplayName("Should save student with responsible")
    void testSaveStudentWithResponsible() {
        Student student = new Student("Pedro Costa", "pedro@email.com", "55555555555", "password");
        student.setId(3);

        Responsible responsible = new Responsible();
        responsible.setResponsibleName("Antonio Costa");
        responsible.setKinship("Pai");
        student.setResponsible(responsible);

        when(studentJpaRepository.save(student)).thenReturn(student);

        Student result = studentCommandJpaAdapter.save(student);

        assertNotNull(result);
        assertNotNull(result.getResponsible());
        assertEquals("Antonio Costa", result.getResponsible().getResponsibleName());
        verify(studentJpaRepository, times(1)).save(student);
    }

    @Test
    @DisplayName("Should handle null student in save")
    void testSaveNullStudent() {
        when(studentJpaRepository.save(null)).thenThrow(new IllegalArgumentException("Student cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> {
            studentCommandJpaAdapter.save(null);
        });
    }

    @Test
    @DisplayName("Should delete multiple students sequentially")
    void testDeleteMultipleStudents() {
        studentCommandJpaAdapter.delete(1);
        studentCommandJpaAdapter.delete(2);
        studentCommandJpaAdapter.delete(3);

        verify(studentJpaRepository, times(3)).deleteById(any());
    }
}

