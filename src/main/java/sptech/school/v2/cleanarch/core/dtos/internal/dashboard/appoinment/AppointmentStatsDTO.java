package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.appoinment;

public record AppointmentStatsDTO(
        long totalAppointments,
        long confirmedCount,
        long activeStudents,
        double averageDuration
) { }
