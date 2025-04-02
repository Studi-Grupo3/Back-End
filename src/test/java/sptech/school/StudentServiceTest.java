
package sptech.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.application.mappers.StudentMapper;
import sptech.school.application.service.StudentService;
import sptech.school.domain.dto.request.StudentRequestUpdateDTO;
import sptech.school.domain.dto.response.StudentResponseDTO;
import sptech.school.domain.entity.Student;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("Dado o uso da StudentService")
class StudentServiceTest {

    @Mock
    private JpaUserRepository<Student> repository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;

    private AutoCloseable mocks;

    private StudentRequestUpdateDTO dtoExemplo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(studentService, "studentMapper", studentMapper);
        ReflectionTestUtils.setField(studentService, "passwordEncoder", passwordEncoder);
        dtoExemplo = new StudentRequestUpdateDTO(
            "Nome Teste",
            "email@exemplo.com",
            "123.456.789-09",
            "senhaSegura",
            "11999999999"
        );
    }

    @Test
    @DisplayName("[1] - Quando um novo estudante for criado, ele deve ser salvo e retornado")
    void deveCriarEstudanteComSucesso() {
        Student student = new Student("Nome Teste", "email@exemplo.com", "senhaSegura", "11999999999", "1123456789");
        Student estudante = new Student();
        estudante.setPassword(student.getPassword());

        when(passwordEncoder.encode(estudante.getPassword())).thenReturn("hashedPassword");
        estudante.setPassword("hashedPassword");
        when(repository.save(estudante)).thenReturn(estudante);
        StudentResponseDTO responseDto = studentMapper.toDtoResponse(estudante);
        when(studentMapper.toDtoResponse(estudante)).thenReturn(responseDto);

        Student result = studentService.create(student);

        assertEquals(responseDto, studentMapper.toDtoResponse(result), "Deveria retornar o mesmo estudante salvo como DTO");
        verify(repository).save(student);
    }

    @Test
    @DisplayName("[2] - Quando validateSpecify for chamado, o estudante deve ser atualizado com os dados do DTO")
    void deveValidarEAtualizarEstudanteComDTO() {
        Student alvo = new Student();

        Student resultado = studentService.validateSpecify(dtoExemplo, alvo);

        verify(studentMapper).updateStudentFromDto(dtoExemplo, alvo);
        assertEquals(alvo, resultado, "Deveria retornar o estudante atualizado");
    }

    @Test
    @DisplayName("[3] - Quando um estudante for encontrado pelo ID, ele deve ser retornado")
    void deveRetornarEstudantePorId() {
        Student estudante = new Student();
        when(repository.findById(1)).thenReturn(Optional.of(estudante));

        Student resultado = studentService.findById(1);

        assertEquals(estudante, resultado, "Deveria retornar o estudante encontrado pelo ID");
    }

    @Test
    @DisplayName("[4] - Quando um estudante não for encontrado pelo ID, deve lançar exceção NOT_FOUND")
    void deveLancarExcecaoQuandoEstudanteNaoEncontradoPorId() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> studentService.findById(99),
            "Deveria lançar NOT_FOUND quando estudante não existir"
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode(), "Status HTTP esperado: 404");
        assertEquals("Student not found.", ex.getReason(), "Mensagem esperada: Student not found.");
    }

    @Test
    @DisplayName("[5] - Quando listar estudantes, deve retornar todos como DTOs")
    void deveListarTodosEstudantesComoDTOs() {
        List<Student> estudantes = List.of(new Student(), new Student());
        // Using proper constructor arguments for StudentResponseDTO (example values)
        StudentResponseDTO dto1 = new StudentResponseDTO("Test Student", "email@test.com", "1123456789", "1123456789");
        StudentResponseDTO dto2 = new StudentResponseDTO( "Test Student 2", "email2@test.com", "1198765432", "1123456789");

        when(repository.findAll()).thenReturn(estudantes);
        when(studentMapper.toDtoResponse(any(Student.class)))
                .thenReturn(dto1)
                .thenReturn(dto2);

        when(repository.findAll()).thenReturn(estudantes);
        when(studentMapper.toDtoResponse(any(Student.class)))
                .thenReturn(dto1)
                .thenReturn(dto2);

        List<StudentResponseDTO> resultado = studentService.listAll();

        assertEquals(2, resultado.size(), "Deveria listar todos os estudantes convertidos em DTO");
        verify(studentMapper, times(2)).toDtoResponse(any(Student.class));
    }

    @Test
    @DisplayName("[6] - Quando deletar um estudante existente, a operação deve ser bem sucedida")
    void deveDeletarEstudanteExistente() {
        when(repository.existsById(1)).thenReturn(true);

        studentService.delete(1);

        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("[7] - Quando deletar um estudante inexistente, deve lançar exceção NOT_FOUND")
    void deveLancarExcecaoAoDeletarEstudanteInexistente() {
        when(repository.existsById(42)).thenReturn(false);

        ResponseStatusException ex = assertThrows(
            ResponseStatusException.class,
            () -> studentService.delete(42),
            "Deveria lançar NOT_FOUND ao tentar deletar estudante inexistente"
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode(), "Status HTTP esperado: 404");
        assertEquals("Appointment not found.", ex.getReason(), "Mensagem esperada: Appointment not found.");
    }
}
