package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment;

public record PaymentStatsDTO(
        double totalAmount,
        long totalTeachers,
        double pendingAmount,
        long pendingTeachers,
        double realizedAmount,
        long realizedTeachers,
        double averageAmountPerTeacher
) {}
