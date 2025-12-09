package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TeacherJpaRepository Tests")
class TeacherJpaRepositoryTest {

    @Test
    @DisplayName("Should validate Teacher entity structure")
    void testTeacherEntityStructure() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);

        Teacher teacher = new Teacher("Prof. Silva", "silva@email.com", "12345678901", "password", subjects);
        teacher.setId(1);

        assertNotNull(teacher);
        assertEquals(1, teacher.getId());
        assertEquals("Prof. Silva", teacher.getName());
        assertEquals("silva@email.com", teacher.getEmail());
        assertTrue(teacher.getSubjects().contains(Subject.MATHEMATICS));
    }

    @Test
    @DisplayName("Should validate Teacher with null subjects")
    void testTeacherWithNullSubjects() {
        Teacher teacher = new Teacher("Prof. João", "joao@email.com", "98765432101", "password", null);

        assertNotNull(teacher);
        assertNull(teacher.getSubjects());
    }

    @Test
    @DisplayName("Should validate Teacher with empty subjects list")
    void testTeacherWithEmptySubjects() {
        Teacher teacher = new Teacher("Prof. Maria", "maria@email.com", "55555555555", "password", new ArrayList<>());

        assertNotNull(teacher);
        assertTrue(teacher.getSubjects().isEmpty());
    }

    @Test
    @DisplayName("Should validate Teacher with multiple subjects")
    void testTeacherWithMultipleSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);
        subjects.add(Subject.PHYSICS);
        subjects.add(Subject.ENGLISH);

        Teacher teacher = new Teacher("Prof. Carlos", "carlos@email.com", "11111111111", "password", subjects);

        assertEquals(3, teacher.getSubjects().size());
        assertTrue(teacher.getSubjects().contains(Subject.MATHEMATICS));
        assertTrue(teacher.getSubjects().contains(Subject.PHYSICS));
        assertTrue(teacher.getSubjects().contains(Subject.ENGLISH));
    }
}

