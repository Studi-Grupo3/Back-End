package sptech.school.v2.cleanarch.infra.persistence.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;
import sptech.school.v2.cleanarch.infra.persistence.repository.TeacherJpaRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TeacherCommandJpaAdapter Tests")
@ExtendWith(MockitoExtension.class)
class TeacherCommandJpaAdapterTest {

    private TeacherCommandJpaAdapter teacherCommandJpaAdapter;

    @Mock
    private TeacherJpaRepository teacherJpaRepository;

    @BeforeEach
    void setUp() {
        teacherCommandJpaAdapter = new TeacherCommandJpaAdapter(teacherJpaRepository);
    }

    @Test
    @DisplayName("Should save a teacher")
    void testSaveTeacher() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);
        Teacher teacher = new Teacher("Prof. Silva", "silva@email.com", "12345678901", "password", subjects);
        teacher.setId(1);

        when(teacherJpaRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherCommandJpaAdapter.save(teacher);

        assertNotNull(result);
        assertEquals("Prof. Silva", result.getName());
        assertEquals("silva@email.com", result.getEmail());
        verify(teacherJpaRepository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Should update a teacher")
    void testUpdateTeacher() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.PHYSICS);
        Teacher teacher = new Teacher("Prof. João", "joao@email.com", "98765432101", "password", subjects);
        teacher.setId(2);

        when(teacherJpaRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherCommandJpaAdapter.update(teacher);

        assertNotNull(result);
        assertEquals("Prof. João", result.getName());
        verify(teacherJpaRepository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Should delete a teacher by ID")
    void testDeleteTeacher() {
        Integer teacherId = 1;

        teacherCommandJpaAdapter.delete(teacherId);

        verify(teacherJpaRepository, times(1)).deleteById(teacherId);
    }

    @Test
    @DisplayName("Should save teacher with multiple subjects")
    void testSaveTeacherWithMultipleSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);
        subjects.add(Subject.ENGLISH);
        subjects.add(Subject.HISTORY);

        Teacher teacher = new Teacher("Prof. Multi", "multi@email.com", "11111111111", "password", subjects);
        teacher.setId(3);

        when(teacherJpaRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherCommandJpaAdapter.save(teacher);

        assertNotNull(result);
        assertEquals(3, result.getSubjects().size());
        verify(teacherJpaRepository, times(1)).save(teacher);
    }

    @Test
    @DisplayName("Should handle null teacher in save")
    void testSaveNullTeacher() {
        when(teacherJpaRepository.save(null)).thenThrow(new IllegalArgumentException("Teacher cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> {
            teacherCommandJpaAdapter.save(null);
        });
    }
}

