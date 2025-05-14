package sptech.school;// src/test/java/sptech/school/application/service/dashboard/AppointmentDashServiceTest.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.adapters.out.persistence.StudentRepositoryJpa;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.application.service.dashboard.AppointmentDashService;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentDashDTO;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentStatsDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.AppointmentStatus;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentDashServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private StudentRepositoryJpa studentRepo;

    @Mock
    private TeacherRepositoryJpa teacherRepo;

    @InjectMocks
    private AppointmentDashService dashService;

    private YearMonth currentMonth;
    private LocalDateTime start, end;

    @BeforeEach
    void setup() {
        currentMonth = YearMonth.now();
        start = currentMonth.atDay(1).atStartOfDay();
        end   = currentMonth.atEndOfMonth().atTime(23, 59, 59);
    }

    @Test
    void deveRetornarEstatisticasCorretasParaUmAgendamentoConfirmado() {
        // dado: um aluno e um professor
        Student aluno = new Student(); aluno.setId(1); aluno.setName("Aluno Teste");
        Teacher prof  = new Teacher(); prof.setId(2); prof.setName("Prof Teste");
        // e um agendamento confirmado de 2h
        Appointment appt = new Appointment();
        appt.setStudent(aluno);
        appt.setTeacher(prof);
        appt.setDateTime(start.plusDays(5).withHour(10));
        appt.setLessonDuration(2.0);
        appt.setStatus(AppointmentStatus.COMPLETED);

        when(appointmentRepository.findByDateTimeBetween(start, end))
                .thenReturn(List.of(appt));

        // quando
        AppointmentDashDTO dash = dashService.getDashboardData();

        // então – stats
        AppointmentStatsDTO stats = dash.getStats();
        assertThat(stats.getTotalAppointments()).isEqualTo(1);
        assertThat(stats.getConfirmedCount()).isEqualTo(1);
        assertThat(stats.getActiveStudents()).isEqualTo(1);
        assertThat(stats.getAverageDuration()).isEqualTo(2.0);

        // weekly chart deve ter exatamente uma entrada em "Semana X"
        assertThat(dash.getWeeklyChart()).hasSize(1)
                .first()
                .extracting("label", "value")
                .containsExactly(
                        "Semana " + appt.getDateTime().get(WeekFields.of(Locale.getDefault()).weekOfMonth()),
                        1.0
                );

        // pie chart: 100% confirmados, 0 pendentes e cancelados
        assertThat(dash.getStatusPie()).extracting("label","percentage")
                .containsExactly(
                        tuple("Confirmados", 100.0),
                        tuple("Pendentes",   0.0),
                        tuple("Cancelados",  0.0)
                );

        // tabela: 1 linha com os nomes e horários corretos
        assertThat(dash.getTable()).hasSize(1);
        var row = dash.getTable().get(0);
        assertThat(row.getStudentName()).isEqualTo("Aluno Teste");
        assertThat(row.getTeacherName()).isEqualTo("Prof Teste");
        assertThat(row.getDuration()).isEqualTo(2.0);
        assertThat(row.getStatus()).isEqualTo("COMPLETED");

        verify(appointmentRepository, times(1))
                .findByDateTimeBetween(start, end);
    }
}
