package sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment;

public record PaymentTableDTO(
        Integer id,
        String name,
        String subject,
        double valuePerHour,
        int hours,
        double total,
        String status
) {}
