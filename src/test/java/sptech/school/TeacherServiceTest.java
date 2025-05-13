
package sptech.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.application.mappers.TeacherMapper;
import sptech.school.application.service.TeacherService;
import sptech.school.domain.dto.request.TeacherRequestUpdateDTO;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.Subject;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Dado o uso da TeacherService")
class TeacherServiceTest {


    @Mock
    private JpaUserRepository<Teacher> repository;
    @Mock
    private TeacherMapper teacherMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private TeacherService teacherService;

    private AutoCloseable mocks;
    private Teacher teacher;
    private TeacherRequestUpdateDTO dtoUpdated;
    private Teacher teacherRequest;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        teacherService = new TeacherService(repository, passwordEncoder);
        ReflectionTestUtils.setField(teacherService, "teacherMapper", teacherMapper);
        ReflectionTestUtils.setField(teacherService, "passwordEncoder", passwordEncoder);
        teacher = new Teacher();
        dtoUpdated = new TeacherRequestUpdateDTO("Nome", "email@teste.com", "12345678900", "senha", Subject.ART);
        teacherRequest = new Teacher("Lula", "Lula@teste.com", "12345678900", "senha", Subject.PHILOSOPHY);
    }

    @Test
    @DisplayName("[1] - Deve criar professor com sucesso")
    void deveCriarProfessor() {
        when(passwordEncoder.encode(teacherRequest.getPassword())).thenReturn("hashedPassword");
        when(repository.save(any(Teacher.class))).thenReturn(teacher);
        Teacher salvo = teacherService.create(teacherRequest);

        assertEquals(teacher, salvo);
    }

    @Test
    @DisplayName("[2] - Deve validar e atualizar professor via DTO")
    void deveValidarEDTO() {
        Teacher result = teacherService.validateSpecify(dtoUpdated, teacher);

        verify(teacherMapper).updateTeacherFromDto(dtoUpdated, teacher);
        assertEquals(teacher, result);
    }

    @Test
    @DisplayName("[3] - Deve retornar professor por ID")
    void deveBuscarPorId() {
        when(repository.findById(1)).thenReturn(Optional.of(teacher));

        Teacher encontrado = teacherService.findById(1);

        assertEquals(teacher, encontrado);
    }

    @Test
    @DisplayName("[4] - Deve lançar exceção ao buscar ID inexistente")
    void deveLancar404Busca() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
            () -> teacherService.findById(99));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("[5] - Deve listar todos professores convertidos para DTO")
    void deveListarTodos() {
        List<Teacher> lista = List.of(teacher, teacher);
        TeacherRequestUpdateDTO dtoMock = new TeacherRequestUpdateDTO("A", "B", "C", "D", Subject.ART);

        when(repository.findAll()).thenReturn(lista);
        when(teacherMapper.toDto(any())).thenReturn(dtoMock);

        List<Teacher> resultado = teacherService.listAll();

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("[6] - Deve deletar professor existente")
    void deveDeletarProfessor() {
        when(repository.existsById(1)).thenReturn(true);

        teacherService.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("[7] - Deve lançar exceção ao deletar ID inexistente")
    void deveErroAoDeletar() {
        when(repository.existsById(42)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> teacherService.delete(42));
    }

    @Test
    @DisplayName("[8] - Deve validar DTO com campos nulos sem lançar erro")
    void validarComCamposNulos() {
        TeacherRequestUpdateDTO dtoParcial = new TeacherRequestUpdateDTO(null, null, null, null, null);

        Teacher res = teacherService.validateSpecify(dtoParcial, teacher);

        assertNotNull(res);
    }

    @Test
    @DisplayName("[9] - Deve tratar múltiplos professores na listagem")
    void listarMuitosProfessores() {
        List<Teacher> lista = Arrays.asList(new Teacher(), new Teacher(), new Teacher());
        when(repository.findAll()).thenReturn(lista);
        when(teacherMapper.toDto(any())).thenReturn(dtoUpdated);

        List<Teacher> resultado = teacherService.listAll();

        assertEquals(3, resultado.size());
    }
}