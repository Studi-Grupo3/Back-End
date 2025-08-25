
package sptech.school;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.application.config.security.user.details.service.TeacherUserDetailsService;
import sptech.school.domain.entity.Teacher;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Dado o uso de TeacherUserDetailsService")
class TeacherUserDetailsServiceTest {

    @Mock
    private TeacherRepositoryJpa teacherRepository;

    @InjectMocks
    private TeacherUserDetailsService service;

    private AutoCloseable mocks;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        teacher = new Teacher();
        teacher.setEmail("professor@teste.com");
        teacher.setPassword("senha123");
    }

    @Test
    @DisplayName("[1] - Deve carregar professor por e-mail")
    void deveCarregarPorEmail() {
        when(teacherRepository.findByEmail("professor@teste.com")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("professor@teste.com");

        assertEquals("professor@teste.com", ud.getUsername());
        assertEquals("senha123", ud.getPassword());
    }

    @Test
    @DisplayName("[2] - Deve lançar exceção se e-mail não existe")
    void deveLancarSeInexistente() {
        when(teacherRepository.findByEmail("invalido@x.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername("invalido@x.com"));
    }

    @Test
    @DisplayName("[3] - Deve conter role TEACHER")
    void deveConterRoleTeacher() {
        when(teacherRepository.findByEmail("professor@teste.com")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("professor@teste.com");

        assertTrue(ud.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER")));
    }

    @Test
    @DisplayName("[4] - Deve verificar chamada ao repositório")
    void deveChamarRepositorio() {
        when(teacherRepository.findByEmail("professor@teste.com")).thenReturn(Optional.of(teacher));

        service.loadUserByUsername("professor@teste.com");

        verify(teacherRepository).findByEmail("professor@teste.com");
    }

    @Test
    @DisplayName("[5] - Deve lidar com letras maiúsculas no e-mail")
    void emailMaiusculo() {
        teacher.setEmail("PROFESSOR@TESTE.COM");
        when(teacherRepository.findByEmail("PROFESSOR@TESTE.COM")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("PROFESSOR@TESTE.COM");

        assertEquals("PROFESSOR@TESTE.COM", ud.getUsername());
    }

    @Test
    @DisplayName("[6] - Deve retornar senha não nula")
    void senhaNaoNula() {
        when(teacherRepository.findByEmail("professor@teste.com")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("professor@teste.com");

        assertNotNull(ud.getPassword());
    }

    @Test
    @DisplayName("[7] - Deve ter apenas uma role")
    void apenasUmaRole() {
        when(teacherRepository.findByEmail("professor@teste.com")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("professor@teste.com");

        assertEquals(1, ud.getAuthorities().size());
    }

    @Test
    @DisplayName("[8] - Deve lançar UsernameNotFoundException com mensagem esperada")
    void mensagemExcecao() {
        when(teacherRepository.findByEmail("errado")).thenReturn(Optional.empty());

        Exception ex = assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername("errado"));

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("[9] - Deve aceitar e-mail com domínio alternativo")
    void emailComDominio() {
        teacher.setEmail("prof@dominio.org");
        when(teacherRepository.findByEmail("prof@dominio.org")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("prof@dominio.org");

        assertEquals("prof@dominio.org", ud.getUsername());
    }

    @Test
    @DisplayName("[10] - Deve retornar UserDetails com campos obrigatórios")
    void camposObrigatorios() {
        when(teacherRepository.findByEmail("professor@teste.com")).thenReturn(Optional.of(teacher));

        UserDetails ud = service.loadUserByUsername("professor@teste.com");

        assertNotNull(ud.getUsername());
        assertNotNull(ud.getPassword());
    }
}
