package sptech.school.application.service.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.adapters.out.persistence.dashboard.TeacherDashRepository;
import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.ChartPieDTO;
import sptech.school.domain.dto.response.dashboard.teacher.TeacherDashboardDTO;
import sptech.school.domain.dto.response.dashboard.teacher.TeacherStatsDTO;
import sptech.school.domain.dto.response.dashboard.teacher.TeacherTableDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Teacher;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherDashService {

    @Autowired
    private TeacherDashRepository teacherRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public TeacherDashboardDTO getDashboardData() {
        List<Teacher> teachers = teacherRepository.findAll();

        YearMonth now = YearMonth.now();
        LocalDateTime startOfMonth = now.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = now.atEndOfMonth().atTime(23, 59, 59);

        List<Appointment> appointmentsThisMonth =
                appointmentRepository.findByDateTimeBetween(startOfMonth, endOfMonth);

        int totalTeachers = teachers.size();

        // calcula horas trabalhadas por professor, evitando nulls
        Map<Integer, Double> hoursPerTeacher = new HashMap<>();
        for (Appointment appointment : appointmentsThisMonth) {
            Integer teacherId = appointment.getTeacher().getId();
            double hours = Optional.ofNullable(appointment.getLessonDuration()).orElse(0.0);
            hoursPerTeacher.merge(teacherId, hours, Double::sum);
        }

        // soma total de horas, filtrando qualquer valor null por segurança
        double totalHours = hoursPerTeacher.values().stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        double averageHours = totalTeachers == 0 ? 0 : totalHours / totalTeachers;

        // calcula média de valor-hora
        double totalHourlyRate = teachers.stream()
                .filter(t -> t.getHourlyRate() != null)
                .mapToDouble(Teacher::getHourlyRate)
                .sum();
        double averageHourlyRate = totalTeachers == 0 ? 0 : totalHourlyRate / totalTeachers;

        // top 5 professores por horas
        List<ChartBarDTO> topTeachers = hoursPerTeacher.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(5)
                .map(entry ->
                        teachers.stream()
                                .filter(t -> t.getId().equals(entry.getKey()))
                                .findFirst()
                                .map(t -> new ChartBarDTO(t.getName(), entry.getValue()))
                                .orElse(null)
                )
                .filter(Objects::nonNull)
                .toList();

        // Distribuição de disciplinas
        // distribuição de disciplinas
        Map<String, Long> countBySubject = teachers.stream()
                .filter(t -> t.getSubject() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getSubject().name(),
                        Collectors.counting()
                ));

        List<ChartPieDTO> disciplineDistribution = countBySubject.entrySet().stream()
                .map(entry -> new ChartPieDTO(
                        entry.getKey(),
                        totalTeachers == 0 ? 0 : (entry.getValue() * 100.0) / totalTeachers
                ))
                .toList();

        // tabela de professores
        List<TeacherTableDTO> table = teachers.stream()
                .map(t -> {
                    TeacherTableDTO dto = new TeacherTableDTO();
                    dto.setName(t.getName());
                    dto.setSubject(t.getSubject() != null ? t.getSubject().name() : "—");
                    dto.setHoursWorked(hoursPerTeacher.getOrDefault(t.getId(), 0.0));
                    dto.setHourlyRate(
                            t.getHourlyRate() != null
                                    ? String.format("R$ %.2f", t.getHourlyRate())
                                    : "—"
                    );
                    dto.setStatus("Active");
                    return dto;
                })
                .toList();

        TeacherStatsDTO stats = new TeacherStatsDTO(
                totalTeachers,
                averageHours,
                averageHourlyRate,
                totalHours
        );

        return new TeacherDashboardDTO(stats, topTeachers, disciplineDistribution, table);
    }
}
