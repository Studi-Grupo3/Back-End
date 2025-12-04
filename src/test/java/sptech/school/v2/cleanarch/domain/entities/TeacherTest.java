package sptech.school.v2.cleanarch.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teacher Entity Tests")
class TeacherTest {

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
    }

    @Test
    @DisplayName("Should create teacher with all parameters")
    void testTeacherConstructor() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);
        subjects.add(Subject.PORTUGUESE);

        Teacher teacherWithParams = new Teacher("Prof. Silva", "silva@email.com", "98765432101", "password123", subjects);

        assertEquals("Prof. Silva", teacherWithParams.getName());
        assertEquals("silva@email.com", teacherWithParams.getEmail());
        assertEquals("98765432101", teacherWithParams.getCpf());
        assertEquals("password123", teacherWithParams.getPassword());
        assertEquals(2, teacherWithParams.getSubjects().size());
    }

    @Test
    @DisplayName("Should set and get ID")
    void testSetGetId() {
        teacher.setId(5);
        assertEquals(5, teacher.getId());
    }

    @Test
    @DisplayName("Should set and get subjects")
    void testSetGetSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.PHYSICS);
        teacher.setSubjects(subjects);

        assertNotNull(teacher.getSubjects());
        assertEquals(1, teacher.getSubjects().size());
        assertTrue(teacher.getSubjects().contains(Subject.PHYSICS));
    }

    @Test
    @DisplayName("Should set and get hourly rate")
    void testSetGetHourlyRate() {
        teacher.setHourlyRate(150.50);
        assertEquals(150.50, teacher.getHourlyRate());
    }

    @Test
    @DisplayName("Should set and get resume teacher")
    void testSetGetResumeTeacher() {
        String resume = "Experiência em ensino fundamental e médio";
        teacher.setResumeTeacher(resume);
        assertEquals(resume, teacher.getResumeTeacher());
    }

    @Test
    @DisplayName("Should set and get years of experience")
    void testSetGetYearsExperience() {
        teacher.setYearsExperience("10");
        assertEquals("10", teacher.getYearsExperience());
    }

    @Test
    @DisplayName("Should set and get academic formation")
    void testSetGetAcademicFormation() {
        teacher.setAcademicFormation("Licenciatura em Matemática");
        assertEquals("Licenciatura em Matemática", teacher.getAcademicFormation());
    }

    @Test
    @DisplayName("Should set and get name")
    void testSetGetName() {
        teacher.setName("Prof. João");
        assertEquals("Prof. João", teacher.getName());
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetGetEmail() {
        teacher.setEmail("professor@email.com");
        assertEquals("professor@email.com", teacher.getEmail());
    }

    @Test
    @DisplayName("Should set and get CPF")
    void testSetGetCpf() {
        teacher.setCpf("98765432101");
        assertEquals("98765432101", teacher.getCpf());
    }

    @Test
    @DisplayName("Should set and get password")
    void testSetGetPassword() {
        teacher.setPassword("securepassword");
        assertEquals("securepassword", teacher.getPassword());
    }

    @Test
    @DisplayName("Should set and get cellphone number")
    void testSetGetCellphoneNumber() {
        teacher.setCellphoneNumber("11988776655");
        assertEquals("11988776655", teacher.getCellphoneNumber());
    }

    @Test
    @DisplayName("Should set and get date of birth")
    void testSetGetDateBirth() {
        LocalDate birthDate = LocalDate.of(1985, 3, 20);
        teacher.setDateBirth(birthDate);
        assertEquals(birthDate, teacher.getDateBirth());
    }

    @Test
    @DisplayName("Should create empty teacher with no args constructor")
    void testEmptyConstructor() {
        Teacher emptyTeacher = new Teacher();
        assertNull(emptyTeacher.getId());
        assertNull(emptyTeacher.getName());
    }

    @Test
    @DisplayName("Should return string representation")
    void testToString() {
        teacher.setId(1);
        List<Subject> subjects = new ArrayList<>();
        subjects.add(Subject.MATHEMATICS);
        teacher.setSubjects(subjects);

        String toString = teacher.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Teacher"));
        assertTrue(toString.contains("id=1"));
    }
}

