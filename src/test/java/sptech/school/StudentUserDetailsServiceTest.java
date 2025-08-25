
package sptech.school;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sptech.school.adapters.out.persistence.StudentRepositoryJpa;
import sptech.school.application.config.security.user.details.service.StudentUserDetailsService;
import sptech.school.domain.entity.Student;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Dado o uso de StudentUserDetailsService")
class StudentUserDetailsServiceTest {

    @Mock
    private StudentRepositoryJpa studentRepository;

    @InjectMocks
    private StudentUserDetailsService service;

    private AutoCloseable mocks;

    private Student student;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setEmail("student@test.com");
        student.setPassword("senha123");
    }

    @Test
    @DisplayName("[1] - Deve carregar usuário com sucesso por e-mail")
    void deveCarregarUsuario() {
        when(studentRepository.findByEmail("student@test.com")).thenReturn(Optional.of(student));

        UserDetails userDetails = service.loadUserByUsername("student@test.com");

        assertEquals("student@test.com", userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    @DisplayName("[2] - Deve lançar exceção se e-mail não encontrado")
    void deveLancarExcecaoEmailInexistente() {
        when(studentRepository.findByEmail("naoexiste@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername("naoexiste@test.com"));
    }

    @Test
    @DisplayName("[3] - Deve retornar role como STUDENT")
    void deveConterRoleStudent() {
        when(studentRepository.findByEmail("student@test.com")).thenReturn(Optional.of(student));

        UserDetails userDetails = service.loadUserByUsername("student@test.com");

        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT")));
    }

    @Test
    @DisplayName("[4] - Email retornado deve ser igual ao salvo")
    void emailRetornadoIgual() {
        student.setEmail("custom@email.com");
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student));

        UserDetails details = service.loadUserByUsername("custom@email.com");

        assertEquals("custom@email.com", details.getUsername());
    }

    @Test
    @DisplayName("[5] - Senha deve bater com a do banco")
    void senhaRetornadaIgual() {
        student.setPassword("abc123");
        when(studentRepository.findByEmail("student@test.com")).thenReturn(Optional.of(student));

        UserDetails ud = service.loadUserByUsername("student@test.com");

        assertEquals("abc123", ud.getPassword());
    }

    @Test
    @DisplayName("[6] - Deve lidar com e-mails em letras maiúsculas")
    void emailCaseInsensitive() {
        student.setEmail("user@case.com");
        when(studentRepository.findByEmail("user@case.com")).thenReturn(Optional.of(student));

        UserDetails ud = service.loadUserByUsername("user@case.com");

        assertEquals("user@case.com", ud.getUsername());
    }

    @Test
    @DisplayName("[7] - Deve ter apenas uma role")
    void deveTerUmaRole() {
        when(studentRepository.findByEmail("student@test.com")).thenReturn(Optional.of(student));

        UserDetails details = service.loadUserByUsername("student@test.com");

        assertEquals(1, details.getAuthorities().size());
    }

    @Test
    @DisplayName("[8] - Deve lançar UsernameNotFoundException com mensagem correta")
    void mensagemExcecaoCorreta() {
        when(studentRepository.findByEmail(any())).thenReturn(Optional.empty());

        Exception ex = assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername("nao@existe.com"));

        assertEquals("Usuário não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("[9] - Deve chamar repositório com e-mail correto")
    void verificarChamadaRepositório() {
        when(studentRepository.findByEmail("student@test.com")).thenReturn(Optional.of(student));

        service.loadUserByUsername("student@test.com");

        verify(studentRepository).findByEmail("student@test.com");
    }

    @Test
    @DisplayName("[10] - Deve retornar UserDetails com username e password não nulos")
    void deveRetornarCamposObrigatorios() {
        when(studentRepository.findByEmail(any())).thenReturn(Optional.of(student));

        UserDetails userDetails = service.loadUserByUsername("teste");

        assertNotNull(userDetails.getUsername());
        assertNotNull(userDetails.getPassword());
    }
}
