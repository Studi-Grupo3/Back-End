package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.overview;

public record OverviewStatsDTO(
        double totalRevenue,
        long totalTeachers,
        double pendingAmount,
        int totalAppointments
) {}
