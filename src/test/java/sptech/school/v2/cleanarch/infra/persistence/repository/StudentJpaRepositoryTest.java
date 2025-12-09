package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.Responsible;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StudentJpaRepository Tests")
class StudentJpaRepositoryTest {

    @Test
    @DisplayName("Should validate Student entity structure")
    void testStudentEntityStructure() {
        Student student = new Student("João Silva", "joao@email.com", "12345678901", "password");
        student.setId(1);
        student.setSchoolGrade("10º ano");
        student.setSchoolName("Escola Municipal");

        assertNotNull(student);
        assertEquals(1, student.getId());
        assertEquals("João Silva", student.getName());
        assertEquals("joao@email.com", student.getEmail());
        assertEquals("10º ano", student.getSchoolGrade());
        assertEquals("Escola Municipal", student.getSchoolName());
    }

    @Test
    @DisplayName("Should validate Student with responsible")
    void testStudentWithResponsible() {
        Student student = new Student("Maria Santos", "maria@email.com", "98765432101", "password");

        Responsible responsible = new Responsible();
        responsible.setResponsibleName("Antonio Santos");
        responsible.setKinship("Pai");
        student.setResponsible(responsible);

        assertNotNull(student.getResponsible());
        assertEquals("Antonio Santos", student.getResponsible().getResponsibleName());
        assertEquals("Pai", student.getResponsible().getKinship());
    }

    @Test
    @DisplayName("Should validate Student without responsible")
    void testStudentWithoutResponsible() {
        Student student = new Student("Pedro Costa", "pedro@email.com", "55555555555", "password");

        assertNull(student.getResponsible());
    }

    @Test
    @DisplayName("Should validate Student email and CPF")
    void testStudentEmailAndCpf() {
        Student student = new Student("Ana Silva", "ana@email.com", "12345678901", "password");

        assertEquals("ana@email.com", student.getEmail());
        assertEquals("12345678901", student.getCpf());
    }

    @Test
    @DisplayName("Should validate Student password")
    void testStudentPassword() {
        Student student = new Student("Carlos", "carlos@email.com", "11111111111", "secure123");

        assertEquals("secure123", student.getPassword());
    }
}

