package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.appointment;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import sptech.school.domain.dto.response.dashboard.ChartBarDTO;
import sptech.school.domain.dto.response.dashboard.ChartPieDTO;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentTableDTO;
import sptech.school.domain.enumerated.AppointmentStatus;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.appointment.AppointmentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.appoinment.AppointmentStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.AppointmentDashRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.AppointmentNext5;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.StatusCount;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppointmentDashQueryJpaAdapter implements AppointmentDashQueryGateway {

    private final AppointmentDashRepository repository;

    public AppointmentDashQueryJpaAdapter(AppointmentDashRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppointmentDashResponseDTO getAppointmentDashData(LocalDateTime start, LocalDateTime end) {
        long total = repository.countTotalBetween(start, end);

        List<StatusCount> statusCounts = repository.countByStatusBetween(start, end);
        Map<String, Long> statusMap = statusCounts.stream()
                .collect(Collectors.toMap(sc -> sc.getStatus().name(), StatusCount::getTotal));

        long confirmed = statusMap.getOrDefault("COMPLETED", 0L);
        long pending   = statusMap.getOrDefault("SCHEDULED", 0L);
        long cancelled = statusMap.getOrDefault("CANCELLED", 0L);

        long activeStudents = repository.countDistinctStudentsBetween(start, end);
        Double avg = repository.averageDurationBetween(start, end);
        double avgDuration = avg == null ? 0.0 : avg;

        List<ChartBarDTO> weekly = repository.countByWeekOfMonthBetween(start, end).stream()
                .map(w -> new ChartBarDTO("Semana " + w.getWeek(), w.getTotal().doubleValue()))
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

        List<AppointmentNext5> next5 = repository.findNextAppointmentsBetween(start, end, PageRequest.of(0, 5));
        List<AppointmentTableDTO> table = next5.stream().map(p -> {
            AppointmentTableDTO dto = new AppointmentTableDTO();
            dto.setStudentName(p.getStudentName());
            dto.setTeacherName(p.getTeacherName());
            if (p.getDateTime() != null) {
                dto.setDate(p.getDateTime().toLocalDate());
                dto.setTime(p.getDateTime().toLocalTime());
            }
            dto.setDuration(p.getDuration());
            dto.setLocation(p.getLocation());
            dto.setStatus(p.getStatus() != null ? p.getStatus().toString() : null);
            return dto;
        }).collect(Collectors.toList());

        AppointmentStatsDTO stats = new AppointmentStatsDTO(total, confirmed, activeStudents, avgDuration);

        return new AppointmentDashResponseDTO(stats, weekly, pie, table);
    }
}
