package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher;

public record TeacherTableDTO(
        String name,
        String subject,
        double hoursWorked,
        String hourlyRate,
        String status
) {}
