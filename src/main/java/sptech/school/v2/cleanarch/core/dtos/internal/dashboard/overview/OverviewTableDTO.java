package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.overview;

public record OverviewTableDTO(
        String teacher,
        String subject,
        Double hourlyRate,
        Double durationClass,
        String status
) {}
