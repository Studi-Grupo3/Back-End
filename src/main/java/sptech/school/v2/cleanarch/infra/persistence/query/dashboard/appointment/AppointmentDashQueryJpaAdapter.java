package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.appointment;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.ChartPieDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentTableDTO;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.appointment.AppointmentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.appoinment.AppointmentStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.AppointmentDashJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.AppointmentNext5;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.StatusCount;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppointmentDashQueryJpaAdapter implements AppointmentDashQueryGateway {

    private final AppointmentDashJpaRepository repository;

    public AppointmentDashQueryJpaAdapter(AppointmentDashJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppointmentDashResponseDTO getAppointmentDashData(LocalDateTime start, LocalDateTime end) {
        long total = repository.countTotalBetween(start, end);

        List<StatusCount> statusCounts = repository.countByStatusBetween(start, end);
        Map<String, Long> statusMap = statusCounts.stream()
                .collect(Collectors.toMap(sc -> String.valueOf(sc.getStatus()), StatusCount::getTotal));

        long confirmed = statusMap.getOrDefault(AppointmentStatus.COMPLETED.name(), 0L);
        long pending   = statusMap.getOrDefault(AppointmentStatus.SCHEDULED.name(), 0L);
        long cancelled = statusMap.getOrDefault(AppointmentStatus.CANCELLED.name(), 0L);

        long activeStudents = repository.countDistinctStudentsBetween(start, end);
        Double avg = repository.averageDurationBetween(start, end);
        double avgDuration = avg == null ? 0.0 : avg;

        List<LocalDateTime> dates = repository.findCompletedLessonDatesBetween(start, end);

        List<ChartBarDTO> weekly = dates.stream()
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.groupingBy(
                        date -> {
                            LocalDate firstOfMonth = date.withDayOfMonth(1);
                            WeekFields weekFields = WeekFields.of(Locale.getDefault());

                            int weekOfYear = date.get(weekFields.weekOfYear());
                            int firstWeekOfYear = firstOfMonth.get(weekFields.weekOfYear());

                            return weekOfYear - firstWeekOfYear + 1;
                        },
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new ChartBarDTO("Semana " + entry.getKey(), entry.getValue().doubleValue()))
                .sorted((a, b) -> {
                    int weekA = Integer.parseInt(a.getLabel().replaceAll("[^0-9]", ""));
                    int weekB = Integer.parseInt(b.getLabel().replaceAll("[^0-9]", ""));
                    return Integer.compare(weekA, weekB);
                })
                .collect(Collectors.toList());

        Map<AppointmentStatus, String> statusLabels = Map.of(
                AppointmentStatus.COMPLETED, "Confirmados",
                AppointmentStatus.SCHEDULED, "Pendentes",
                AppointmentStatus.CANCELLED, "Cancelados"
        );

        List<ChartPieDTO> pie = List.of(
                new ChartPieDTO(statusLabels.get(AppointmentStatus.COMPLETED),
                        total == 0 ? 0.0 : confirmed * 100.0 / total),
                new ChartPieDTO(statusLabels.get(AppointmentStatus.SCHEDULED),
                        total == 0 ? 0.0 : pending * 100.0 / total),
                new ChartPieDTO(statusLabels.get(AppointmentStatus.CANCELLED),
                        total == 0 ? 0.0 : cancelled * 100.0 / total)
        );

        List<AppointmentNext5> allAppointments = repository.findNextAppointmentsBetween(start, end, PageRequest.of(0, Integer.MAX_VALUE));

        List<AppointmentTableDTO> table = allAppointments.stream().map(p -> {
            AppointmentTableDTO dto = new AppointmentTableDTO();
            dto.setStudentName(p.getStudentName());
            dto.setTeacherName(p.getTeacherName());
            if (p.getDateTime() != null) {
                dto.setDate(p.getDateTime().toLocalDate());
                dto.setTime(p.getDateTime().toLocalTime());
            }
            dto.setDuration(p.getDuration());
            dto.setLocation(p.getLocation());
            dto.setStatus(p.getStatus() != null ? String.valueOf(p.getStatus()) : null);
            return dto;
        }).collect(Collectors.toList());

        AppointmentStatsDTO stats = new AppointmentStatsDTO(total, confirmed, activeStudents, avgDuration);

        return new AppointmentDashResponseDTO(stats, weekly, pie, table);
    }
}