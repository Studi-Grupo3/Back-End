package sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment;

import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.payment.PaymentTableDTO;

import java.util.List;

public record PaymentDashResponseDTO(
        PaymentStatsDTO stats,
        List<PaymentTableDTO> recent
) {}
