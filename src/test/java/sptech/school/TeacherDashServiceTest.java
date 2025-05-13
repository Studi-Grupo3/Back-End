package sptech.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.adapters.out.persistence.dashboard.TeacherDashRepository;
import sptech.school.application.service.dashboard.TeacherDashService;
import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.teacher.TeacherDashboardDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.Subject;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TeacherDashServiceTest {

    @Mock
    private TeacherDashRepository teacherRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private TeacherDashService teacherDashService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardData() {
        // Mockando os professores com valorHora
        Teacher teacher1 = new Teacher("Professor A", "professorA@escola.com", "12345678901", "senha123", Subject.MATHEMATICS);
        teacher1.setId(1);
        teacher1.setHourlyRate(50.0); // Definindo valorHora
        Teacher teacher2 = new Teacher("Professor B", "professorB@escola.com", "98765432100", "senha456", Subject.PHYSICS);
        teacher2.setId(2);
        teacher2.setHourlyRate(60.0); // Definindo valorHora
        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher2));

        // Mockando os estudantes
        Student student1 = new Student("Aluno A", "alunoA@escola.com", "12345678900", "senha123", "11999999999");
        student1.setId(1);
        Student student2 = new Student("Aluno B", "alunoB@escola.com", "98765432111", "senha456", "11988888888");
        student2.setId(2);

        // Mockando os agendamentos
        Appointment appointment1 = new Appointment(student1, teacher1, LocalDateTime.now(), 2.0, "Sala 101");
        Appointment appointment2 = new Appointment(student2, teacher2, LocalDateTime.now(), 3.0, "Sala 102");
        when(appointmentRepository.findByDateTimeBetween(any(), any()))
                .thenReturn(List.of(appointment1, appointment2));

        // Executando o método
        TeacherDashboardDTO dashboardData = teacherDashService.getDashboardData();

        // Verificando os resultados
        assertEquals(2, dashboardData.getStats().getTotalProfessores());
        assertEquals(5.0, dashboardData.getStats().getTotalHorasTrabalhadas());
        assertEquals(2.5, dashboardData.getStats().getMediaHorasMes());
        assertEquals(55.0, dashboardData.getStats().getValorHoraMedio());
        verify(teacherRepository, times(1)).findAll();
        verify(appointmentRepository, times(1)).findByDateTimeBetween(any(), any());
    }

    @Test
    void testTop5Professores() {
        // --- prepara mocks ---
        Teacher teacher1 = new Teacher("Prof A", "a@email.com", "111", "senha", Subject.MATHEMATICS);
        teacher1.setId(1);
        teacher1.setHourlyRate(50.0);
        Teacher teacher2 = new Teacher("Prof B", "b@email.com", "222", "senha", Subject.PHYSICS);
        teacher2.setId(2);
        teacher2.setHourlyRate(60.0);
        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher2));

        Student aluno = new Student("Aluno", "aluno@email.com", "000", "senha", "11999999999");
        aluno.setId(1);

        // Ap1 dá 1.5h ao Prof A, Ap2 dá 3.0h ao Prof B
        Appointment ap1 = new Appointment(aluno, teacher1, LocalDateTime.now(), 1.5, "Sala 1");
        Appointment ap2 = new Appointment(aluno, teacher2, LocalDateTime.now(), 3.0, "Sala 2");
        when(appointmentRepository.findByDateTimeBetween(any(), any()))
                .thenReturn(List.of(ap1, ap2));

        // --- executa e verifica ---
        TeacherDashboardDTO dto = teacherDashService.getDashboardData();
        List<ChartBarDTO> top = dto.getTopTeachers();

        assertEquals(2, top.size(), "Deve retornar exatamente 2 professores");

        // 1º: Prof B (3.0h)
        assertEquals("Prof B", top.get(0).getLabel(),   "O primeiro deve ser o Prof B");
        assertEquals(3.0,      top.get(0).getValue(),   "Valor de horas do Prof B deve ser 3.0");

        // 2º: Prof A (1.5h)
        assertEquals("Prof A", top.get(1).getLabel(),   "O segundo deve ser o Prof A");
        assertEquals(1.5,      top.get(1).getValue(),   "Valor de horas do Prof A deve ser 1.5");
    }

    @Test
    void testDistribuicaoPorDisciplina() {
        Teacher teacher1 = new Teacher("Prof A", "a@email.com", "111", "senha", Subject.MATHEMATICS);
        teacher1.setId(1);
        teacher1.setHourlyRate(50.0);
        Teacher teacher2 = new Teacher("Prof B", "b@email.com", "222", "senha", Subject.MATHEMATICS);
        teacher2.setId(2);
        teacher2.setHourlyRate(60.0);
        Teacher teacher3 = new Teacher("Prof C", "c@email.com", "333", "senha", Subject.PHYSICS);
        teacher3.setId(3);
        teacher3.setHourlyRate(70.0);
        when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher2, teacher3));

        when(appointmentRepository.findByDateTimeBetween(any(), any())).thenReturn(List.of());

        TeacherDashboardDTO dto = teacherDashService.getDashboardData();

        assertEquals(2, dto.getSubsjectChartValues().size());
        assertTrue(dto.getSubsjectChartValues().stream()
                .anyMatch(p -> p.getLabel().equals("MATHEMATICS") && Math.abs(p.getPercentage() - 66.666) < 0.1));
        assertTrue(dto.getSubsjectChartValues().stream()
                .anyMatch(p -> p.getLabel().equals("PHYSICS") && Math.abs(p.getPercentage() - 33.333) < 0.1));
    }

    @Test
    void testTabelaProfessores() {
        Teacher teacher1 = new Teacher("Prof A", "a@email.com", "111", "senha", Subject.MATHEMATICS);
        teacher1.setId(1);
        teacher1.setHourlyRate(55.5);

        when(teacherRepository.findAll()).thenReturn(List.of(teacher1));

        Student student = new Student("Aluno", "aluno@email.com", "000", "senha", "11999999999");
        student.setId(1);

        Appointment ap1 = new Appointment(student, teacher1, LocalDateTime.now(), 2.0, "Sala");
        when(appointmentRepository.findByDateTimeBetween(any(), any())).thenReturn(List.of(ap1));

        TeacherDashboardDTO dto = teacherDashService.getDashboardData();

        assertEquals(1, dto.getTeacherTableValues().size());
        assertEquals("Prof A", dto.getTeacherTableValues().get(0).getName());
        assertEquals("MATHEMATICS", dto.getTeacherTableValues().get(0).getSubject());
        assertEquals(2.0, dto.getTeacherTableValues().get(0).getHoursWorked());
        assertEquals("R$ 55,50", dto.getTeacherTableValues().get(0).getHourlyRate());
        assertEquals("Ativo", dto.getTeacherTableValues().get(0).getStatus());
    }

}