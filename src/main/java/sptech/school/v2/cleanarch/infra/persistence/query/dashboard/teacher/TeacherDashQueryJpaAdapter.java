package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.teacher;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.teacher.TeacherDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartPieDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher.TeacherStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher.TeacherTableDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.teacher.TeacherDashResponseDTO;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.TeacherDashRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.projections.HoursByTeacher;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.projections.TeacherBasicProjection;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TeacherDashQueryJpaAdapter implements TeacherDashQueryGateway {

    private final TeacherDashRepository repository;

    public TeacherDashQueryJpaAdapter(TeacherDashRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeacherDashResponseDTO getTeacherDashData(LocalDateTime start, LocalDateTime end) {
        List<TeacherBasicProjection> teachers = repository.findAllBasic();

        List<HoursByTeacher> hoursList = repository.sumHoursPerTeacherBetween(start, end);
        Map<Integer, Double> hoursPerTeacher = hoursList.stream()
                .collect(Collectors.toMap(HoursByTeacher::getTeacherId, h -> Optional.ofNullable(h.getHours()).orElse(0.0)));

        int totalTeachers = teachers.size();

        double totalHours = hoursPerTeacher.values().stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).sum();

        double averageHours = totalTeachers == 0 ? 0.0 : totalHours / totalTeachers;

        double totalHourlyRate = teachers.stream().filter(t -> t.getHourlyRate() != null).mapToDouble(TeacherBasicProjection::getHourlyRate).sum();
        double averageHourlyRate = totalTeachers == 0 ? 0.0 : totalHourlyRate / totalTeachers;

        List<Map.Entry<Integer, Double>> top = hoursPerTeacher.entrySet().stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .limit(5)
                .toList();

        List<ChartBarDTO> topTeachers = top.stream()
                .map(entry -> {
                    Integer id = entry.getKey();
                    return teachers.stream()
                            .filter(t -> t.getId().equals(id))
                            .findFirst()
                            .map(t -> new ChartBarDTO(t.getName(), entry.getValue()))
                            .orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Map<String, Long> countBySubject = teachers.stream()
                .filter(t -> t.getSubjects() != null)
                .collect(Collectors.groupingBy(TeacherBasicProjection::getSubjects, Collectors.counting()));

        List<ChartPieDTO> disciplineDistribution = countBySubject.entrySet().stream()
                .map(e -> new ChartPieDTO(e.getKey(), totalTeachers == 0 ? 0.0 : (e.getValue() * 100.0) / totalTeachers))
                .collect(Collectors.toList());

        List<TeacherTableDTO> table = teachers.stream()
                .map(t -> {
                    String name = t.getName();
                    String subject = t.getSubjects() == null ? "—" : t.getSubjects();
                    Double hoursWorked = hoursPerTeacher.getOrDefault(t.getId(), 0.0);
                    String hourlyRate = t.getHourlyRate() != null ? String.format("R$ %.2f", t.getHourlyRate()) : "—";
                    String status = "Active";
                    return new TeacherTableDTO(name, subject, hoursWorked, hourlyRate, status);
                })
                .collect(Collectors.toList());

        TeacherStatsDTO stats = new TeacherStatsDTO(totalTeachers, averageHours, averageHourlyRate, totalHours);

        return new TeacherDashResponseDTO(stats, topTeachers, disciplineDistribution, table);
    }
}
