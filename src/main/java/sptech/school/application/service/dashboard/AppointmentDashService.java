package sptech.school.application.service.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.adapters.out.persistence.StudentRepositoryJpa;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.ChartPieDTO;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentDashDTO;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentStatsDTO;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentTableDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentDashService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StudentRepositoryJpa studentRepository;

    @Autowired
    private TeacherRepositoryJpa teacherRepository;

    public AppointmentDashDTO getDashboardData() {
        // período: mês corrente
        YearMonth now = YearMonth.now();
        LocalDateTime inicio = now.atDay(1).atStartOfDay();
        LocalDateTime fim    = now.atEndOfMonth().atTime(23, 59, 59);

        List<Appointment> appts = appointmentRepository.findByDateTimeBetween(inicio, fim);

        int totalAppointments = appts.size();
        long confirmed = appts.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
                .count();
        long pending   = appts.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED)
                .count();
        long cancelled = appts.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED)
                .count();

        // alunos distintos
        long activeStudents = appts.stream()
                .map(a -> a.getStudent().getId())
                .distinct()
                .count();

        // duração média
        double avgDuration = appts.stream()
                .map(Appointment::getLessonDuration)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        // agendamentos por semana (1 a 5)
        WeekFields wf = WeekFields.of(Locale.getDefault());
        Map<Integer, Long> byWeek = appts.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getDateTime().get(wf.weekOfMonth()),
                        Collectors.counting()
                ));
        List<ChartBarDTO> weekly = byWeek.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new ChartBarDTO("Semana " + e.getKey(), e.getValue().doubleValue()))
                .toList();

        // distribuição por status
        List<ChartPieDTO> pie = List.of(
                new ChartPieDTO("Confirmados", totalAppointments == 0 ? 0 : confirmed * 100.0 / totalAppointments),
                new ChartPieDTO("Pendentes",  totalAppointments == 0 ? 0 : pending   * 100.0 / totalAppointments),
                new ChartPieDTO("Cancelados", totalAppointments == 0 ? 0 : cancelled * 100.0 / totalAppointments)
        );

        // tabela de próximos 5 agendamentos (ordenados pela data)
        List<AppointmentTableDTO> table = appts.stream()
                .sorted(Comparator.comparing(Appointment::getDateTime))
                .limit(5)
                .map(a -> {
                    Student s = a.getStudent();
                    Teacher t = a.getTeacher();
                    AppointmentTableDTO dto = new AppointmentTableDTO();
                    dto.setStudentName(s.getName());
                    dto.setTeacherName(t.getName());
                    dto.setDate(a.getDateTime().toLocalDate());
                    dto.setTime(a.getDateTime().toLocalTime());
                    dto.setDuration(a.getLessonDuration());
                    dto.setLocation(a.getLocation());
                    dto.setStatus(a.getStatus().toString());
                    return dto;
                })
                .toList();

        // status geral
        AppointmentStatsDTO stats = new AppointmentStatsDTO(
                totalAppointments,
                confirmed,
                activeStudents,
                avgDuration
        );

        return new AppointmentDashDTO(stats, weekly, pie, table);
    }
}
