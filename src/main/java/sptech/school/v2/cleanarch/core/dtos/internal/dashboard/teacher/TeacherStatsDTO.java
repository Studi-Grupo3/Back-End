package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.teacher;

public record TeacherStatsDTO(
        int totalProfessores,
        double mediaHorasMes,
        double valorHoraMedio,
        double totalHorasTrabalhadas
) {}
