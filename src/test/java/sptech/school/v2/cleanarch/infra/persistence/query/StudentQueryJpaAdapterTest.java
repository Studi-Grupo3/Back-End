package sptech.school.v2.cleanarch.infra.persistence.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.infra.persistence.repository.StudentJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("StudentQueryJpaAdapter Tests")
@ExtendWith(MockitoExtension.class)
class StudentQueryJpaAdapterTest {

    private StudentQueryJpaAdapter studentQueryJpaAdapter;

    @Mock
    private StudentJpaRepository studentJpaRepository;

    @BeforeEach
    void setUp() {
        studentQueryJpaAdapter = new StudentQueryJpaAdapter(studentJpaRepository);
    }

    @Test
    @DisplayName("Should return true when student exists by email")
    void testStudentExistsByEmailTrue() {
        when(studentJpaRepository.existsByEmail("student@email.com")).thenReturn(true);

        boolean result = studentQueryJpaAdapter.studentExistsByEmail("student@email.com");

        assertTrue(result);
        verify(studentJpaRepository, times(1)).existsByEmail("student@email.com");
    }

    @Test
    @DisplayName("Should return false when student does not exist by email")
    void testStudentExistsByEmailFalse() {
        when(studentJpaRepository.existsByEmail("student@email.com")).thenReturn(false);

        boolean result = studentQueryJpaAdapter.studentExistsByEmail("student@email.com");

        assertFalse(result);
        verify(studentJpaRepository, times(1)).existsByEmail("student@email.com");
    }

    @Test
    @DisplayName("Should return true when student exists by CPF")
    void testStudentExistsByCpfTrue() {
        when(studentJpaRepository.existsByCpf("12345678901")).thenReturn(true);

        boolean result = studentQueryJpaAdapter.studentExistsByCpf("12345678901");

        assertTrue(result);
        verify(studentJpaRepository, times(1)).existsByCpf("12345678901");
    }

    @Test
    @DisplayName("Should return false when student does not exist by CPF")
    void testStudentExistsByCpfFalse() {
        when(studentJpaRepository.existsByCpf("12345678901")).thenReturn(false);

        boolean result = studentQueryJpaAdapter.studentExistsByCpf("12345678901");

        assertFalse(result);
        verify(studentJpaRepository, times(1)).existsByCpf("12345678901");
    }

    @Test
    @DisplayName("Should find student by ID")
    void testFindById() {
        Student student = new Student();
        student.setId(1);
        student.setName("João Silva");

        when(studentJpaRepository.findById(1)).thenReturn(Optional.of(student));

        Student result = studentQueryJpaAdapter.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("João Silva", result.getName());
        verify(studentJpaRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should return null when student not found by ID")
    void testFindByIdNotFound() {
        when(studentJpaRepository.findById(999)).thenReturn(Optional.empty());

        Student result = studentQueryJpaAdapter.findById(999);

        assertNull(result);
        verify(studentJpaRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Should list all students with pagination")
    void testListAll() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setId(1);
        student.setName("João Silva");
        students.add(student);

        Page<Student> page = new PageImpl<>(students);
        Pageable pageable = PageRequest.of(0, 10);

        when(studentJpaRepository.findAll(pageable)).thenReturn(page);

        Page<Student> result = studentQueryJpaAdapter.listAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(studentJpaRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should find student by email")
    void testFindByEmail() {
        Student student = new Student();
        student.setId(1);
        student.setEmail("student@email.com");
        student.setName("João Silva");

        when(studentJpaRepository.findByEmail("student@email.com")).thenReturn(Optional.of(student));

        Optional<Student> result = studentQueryJpaAdapter.findByEmail("student@email.com");

        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getName());
        verify(studentJpaRepository, times(1)).findByEmail("student@email.com");
    }

    @Test
    @DisplayName("Should return empty optional when student not found by email")
    void testFindByEmailNotFound() {
        when(studentJpaRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        Optional<Student> result = studentQueryJpaAdapter.findByEmail("notfound@email.com");

        assertFalse(result.isPresent());
        verify(studentJpaRepository, times(1)).findByEmail("notfound@email.com");
    }

    @Test
    @DisplayName("Should find student ID by email")
    void testFindIdByEmail() {
        when(studentJpaRepository.findIdByEmail("student@email.com")).thenReturn(Optional.empty());

        Optional<Integer> result = studentQueryJpaAdapter.findIdByEmail("student@email.com");

        assertFalse(result.isPresent());
        verify(studentJpaRepository, times(1)).findIdByEmail("student@email.com");
    }

    @Test
    @DisplayName("Should find student ID by CPF")
    void testFindByCpf() {
        when(studentJpaRepository.findIdByCpf("12345678901")).thenReturn(Optional.empty());

        Optional<Integer> result = studentQueryJpaAdapter.findByCpf("12345678901");

        assertFalse(result.isPresent());
        verify(studentJpaRepository, times(1)).findIdByCpf("12345678901");
    }
}

