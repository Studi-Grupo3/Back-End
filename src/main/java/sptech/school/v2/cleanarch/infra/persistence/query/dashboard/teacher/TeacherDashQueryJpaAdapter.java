package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.teacher;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.teacher.TeacherDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartPieDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher.TeacherTableDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher.TeacherDashResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.TeacherDashJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.projections.HoursByTeacher;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TeacherDashQueryJpaAdapter implements TeacherDashQueryGateway {

    private final TeacherDashJpaRepository repository;

    public TeacherDashQueryJpaAdapter(TeacherDashJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeacherDashResponseDTO getTeacherDashData(LocalDateTime start, LocalDateTime end) {
        List<Teacher> teachers = repository.findAllBasic();
        int totalTeachers = teachers.size();

        List<Teacher> activeTeachers = teachers.stream()
                .filter(t -> !t.isDeleted())
                .toList();
        int totalActive = activeTeachers.size();

        List<HoursByTeacher> hoursList = repository.sumHoursPerTeacherBetween(start, end);
        Map<Integer, Double> hoursPerTeacher = hoursList.stream()
                .collect(Collectors.toMap(
                        HoursByTeacher::getTeacherId,
                        h -> Optional.ofNullable(h.getHours()).orElse(0.0)
                ));

        double totalHoursActive = activeTeachers.stream()
                .map(t -> hoursPerTeacher.getOrDefault(t.getId(), 0.0))
                .mapToDouble(Double::doubleValue)
                .sum();
        double averageHoursActive = totalActive == 0 ? 0.0 : totalHoursActive / totalActive;

        double totalHourlyRateActive = activeTeachers.stream()
                .filter(t -> t.getHourlyRate() != null)
                .mapToDouble(Teacher::getHourlyRate)
                .sum();
        double averageHourlyRateActive = totalActive == 0 ? 0.0 : totalHourlyRateActive / totalActive;

        TeacherStatsDTO stats = new TeacherStatsDTO(totalTeachers, averageHoursActive, averageHourlyRateActive, totalHoursActive);

        List<Map.Entry<Integer, Double>> top = hoursPerTeacher.entrySet().stream()
                .filter(e -> activeTeachers.stream().anyMatch(t -> t.getId().equals(e.getKey())))
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(5)
                .toList();

        List<ChartBarDTO> topTeachers = top.stream()
                .map(entry -> {
                    Integer id = entry.getKey();
                    return activeTeachers.stream()
                            .filter(t -> t.getId().equals(id))
                            .findFirst()
                            .map(t -> new ChartBarDTO(t.getName(), entry.getValue()))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Long> countBySubject = teachers.stream()
                .flatMap(t -> Optional.ofNullable(t.getSubjects()).orElse(List.of()).stream())
                .map(Subject::name)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        List<ChartPieDTO> disciplineDistribution = countBySubject.entrySet().stream()
                .map(e -> new ChartPieDTO(e.getKey(), totalTeachers == 0 ? 0.0 : (e.getValue() * 100.0) / totalTeachers))
                .collect(Collectors.toList());

        List<TeacherTableDTO> table = teachers.stream()
                .map(t -> {
                    String name = t.getName();
                    String subject = Optional.ofNullable(t.getSubjects()).orElse(List.of())
                            .stream()
                            .map(Subject::name)
                            .collect(Collectors.joining(","));
                    if (subject.isBlank()) subject = "—";
                    Double hoursWorked = hoursPerTeacher.getOrDefault(t.getId(), 0.0);
                    String hourlyRate = t.getHourlyRate() != null ? String.format("R$ %.2f", t.getHourlyRate()) : "—";
                    String status = t.isDeleted() ? "Inactive" : "Active";
                    return new TeacherTableDTO(name, subject, hoursWorked, hourlyRate, status);
                })
                .collect(Collectors.toList());

        return new TeacherDashResponseDTO(stats, topTeachers, disciplineDistribution, table);
    }
}
