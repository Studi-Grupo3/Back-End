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
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.infra.persistence.query.teacher.TeacherQueryJpaAdapter;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("TeacherQueryJpaAdapter Tests")
@ExtendWith(MockitoExtension.class)
class TeacherQueryJpaAdapterTest {

    private TeacherQueryJpaAdapter teacherQueryJpaAdapter;

    @Mock
    private TeacherJpaRepository teacherJpaRepository;

    @BeforeEach
    void setUp() {
        teacherQueryJpaAdapter = new TeacherQueryJpaAdapter(teacherJpaRepository);
    }

    @Test
    @DisplayName("Should return true when teacher exists by email")
    void testTeacherExistsByEmailTrue() {
        when(teacherJpaRepository.existsByEmail("prof@email.com")).thenReturn(true);

        boolean result = teacherQueryJpaAdapter.teacherExistsByEmail("prof@email.com");

        assertTrue(result);
        verify(teacherJpaRepository, times(1)).existsByEmail("prof@email.com");
    }

    @Test
    @DisplayName("Should return false when teacher does not exist by email")
    void testTeacherExistsByEmailFalse() {
        when(teacherJpaRepository.existsByEmail("prof@email.com")).thenReturn(false);

        boolean result = teacherQueryJpaAdapter.teacherExistsByEmail("prof@email.com");

        assertFalse(result);
        verify(teacherJpaRepository, times(1)).existsByEmail("prof@email.com");
    }

    @Test
    @DisplayName("Should find teacher by ID")
    void testFindById() {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Prof. Silva");

        when(teacherJpaRepository.findById(1)).thenReturn(Optional.of(teacher));

        Teacher result = teacherQueryJpaAdapter.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Prof. Silva", result.getName());
        verify(teacherJpaRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should return null when teacher not found by ID")
    void testFindByIdNotFound() {
        when(teacherJpaRepository.findById(999)).thenReturn(Optional.empty());

        Teacher result = teacherQueryJpaAdapter.findById(999);

        assertNull(result);
        verify(teacherJpaRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("Should return true when teacher exists by CPF")
    void testTeacherExistsByCpfTrue() {
        when(teacherJpaRepository.existsByCpf("12345678901")).thenReturn(true);

        boolean result = teacherQueryJpaAdapter.teacherExistsByCpf("12345678901");

        assertTrue(result);
        verify(teacherJpaRepository, times(1)).existsByCpf("12345678901");
    }

    @Test
    @DisplayName("Should return false when teacher does not exist by CPF")
    void testTeacherExistsByCpfFalse() {
        when(teacherJpaRepository.existsByCpf("12345678901")).thenReturn(false);

        boolean result = teacherQueryJpaAdapter.teacherExistsByCpf("12345678901");

        assertFalse(result);
        verify(teacherJpaRepository, times(1)).existsByCpf("12345678901");
    }

    @Test
    @DisplayName("Should list all teachers with pagination")
    void testListAll() {
        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId(1);
        teacher1.setName("Prof. Silva");
        teachers.add(teacher1);

        Page<Teacher> page = new PageImpl<>(teachers);
        Pageable pageable = PageRequest.of(0, 10);

        when(teacherJpaRepository.findAll(pageable)).thenReturn(page);

        Page<Teacher> result = teacherQueryJpaAdapter.listAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(teacherJpaRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Should find teacher by email")
    void testFindByEmail() {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setEmail("prof@email.com");
        teacher.setName("Prof. Silva");

        when(teacherJpaRepository.findByEmail("prof@email.com")).thenReturn(Optional.of(teacher));

        Optional<Teacher> result = teacherQueryJpaAdapter.findByEmail("prof@email.com");

        assertTrue(result.isPresent());
        assertEquals("Prof. Silva", result.get().getName());
        verify(teacherJpaRepository, times(1)).findByEmail("prof@email.com");
    }

    @Test
    @DisplayName("Should return empty optional when teacher not found by email")
    void testFindByEmailNotFound() {
        when(teacherJpaRepository.findByEmail("notfound@email.com")).thenReturn(Optional.empty());

        Optional<Teacher> result = teacherQueryJpaAdapter.findByEmail("notfound@email.com");

        assertFalse(result.isPresent());
        verify(teacherJpaRepository, times(1)).findByEmail("notfound@email.com");
    }

    @Test
    @DisplayName("Should find teacher ID by email")
    void testFindIdByEmail() {
        when(teacherJpaRepository.findIdByEmail("prof@email.com")).thenReturn(Optional.of(5));

        Optional<Integer> result = teacherQueryJpaAdapter.findIdByEmail("prof@email.com");

        assertTrue(result.isPresent());
        assertEquals(5, result.get());
        verify(teacherJpaRepository, times(1)).findIdByEmail("prof@email.com");
    }

    @Test
    @DisplayName("Should return empty optional when teacher ID not found by email")
    void testFindIdByEmailNotFound() {
        when(teacherJpaRepository.findIdByEmail("notfound@email.com")).thenReturn(Optional.empty());

        Optional<Integer> result = teacherQueryJpaAdapter.findIdByEmail("notfound@email.com");

        assertFalse(result.isPresent());
        verify(teacherJpaRepository, times(1)).findIdByEmail("notfound@email.com");
    }

    @Test
    @DisplayName("Should find teacher ID by CPF")
    void testFindByCpf() {
        when(teacherJpaRepository.findIdByCpf("12345678901")).thenReturn(Optional.of(3));

        Optional<Integer> result = teacherQueryJpaAdapter.findByCpf("12345678901");

        assertTrue(result.isPresent());
        assertEquals(3, result.get());
        verify(teacherJpaRepository, times(1)).findIdByCpf("12345678901");
    }

    @Test
    @DisplayName("Should return empty optional when teacher ID not found by CPF")
    void testFindByCpfNotFound() {
        when(teacherJpaRepository.findIdByCpf("99999999999")).thenReturn(Optional.empty());

        Optional<Integer> result = teacherQueryJpaAdapter.findByCpf("99999999999");

        assertFalse(result.isPresent());
        verify(teacherJpaRepository, times(1)).findIdByCpf("99999999999");
    }
}

