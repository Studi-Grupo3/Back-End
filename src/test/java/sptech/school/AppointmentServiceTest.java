
package sptech.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.application.mappers.AppointmentMapper;
import sptech.school.application.service.AppointmentService;
import sptech.school.application.service.StudentService;
import sptech.school.application.service.TeacherService;
import sptech.school.domain.dto.AppointmentDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.AppointmentStatus;
import sptech.school.domain.enumerated.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock private AppointmentRepository appointmentRepository;
    @Mock private AppointmentMapper appointmentMapper;
    @Mock private TeacherService teacherService;
    @Mock private StudentService studentService;

    @InjectMocks
    private AppointmentService appointmentService;

    private AutoCloseable mocks;
    private AppointmentDTO dto;
    private Student student;
    private Teacher teacher;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);

        dto = new AppointmentDTO(1, 2, LocalDateTime.now().plusHours(1), 60.0, "SCHEDULED", "Sala A" );
        student = new Student();
        teacher = new Teacher();
        appointment = new Appointment(student, teacher, dto.dateTime(), dto.lessonDuration(), dto.location());
    }

    @Test
    @DisplayName("[2] Deve criar Appointment com sucesso")
    void deveCriarCompromissoComSucesso() {
        when(studentService.findById(dto.idStudent())).thenReturn(student);
        when(teacherService.findById(dto.idTeacher())).thenReturn(teacher);
        when(appointmentRepository.existsByStudentIdAndTeacherIdAndDateTime(anyInt(), anyInt(), any())).thenReturn(false);
        when(appointmentRepository.save(any())).thenReturn(appointment);

        Appointment resultado = appointmentService.create(dto);

        assertNotNull(resultado);
        verify(appointmentRepository).save(any());
    }

    @Test
    @DisplayName("[3] Deve lançar exceção em Appointment duplicado")
    void deveLancarExcecaoCompromissoDuplicado() {
        when(studentService.findById(dto.idStudent())).thenReturn(student);
        when(teacherService.findById(dto.idTeacher())).thenReturn(teacher);
        when(appointmentRepository.existsByStudentIdAndTeacherIdAndDateTime(anyInt(), anyInt(), any())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> appointmentService.create(dto));
    }

    @Test
    @DisplayName("[1] Deve buscar Appointment por ID existente")
    void deveBuscarCompromissoPorId() {
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(appointment));

        Appointment resultado = appointmentService.findById(1);

        assertEquals(appointment, resultado);
    }

    @Test
    @DisplayName("[4] Deve lançar exceção ao buscar ID inexistente")
    void deveLancarAoBuscarIdInvalido() {
        when(appointmentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> appointmentService.findById(99));
    }

    @Test
    @DisplayName("[5] Deve listar todos os Appointments convertidos")
    void deveListarTodos() {
        List<Appointment> lista = List.of(appointment, appointment);
        when(appointmentRepository.findAll()).thenReturn(lista);
        when(appointmentMapper.toDto(any())).thenReturn(dto);

        List<AppointmentDTO> resultado = appointmentService.listAll();

        assertEquals(2, resultado.size());
    }
}
