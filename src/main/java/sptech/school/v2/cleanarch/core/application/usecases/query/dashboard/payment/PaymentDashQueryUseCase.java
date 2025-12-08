package sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.payment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.payment.PaymentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

@Service
public class PaymentDashQueryUseCase {
    private final PaymentDashQueryGateway gateway;

    public PaymentDashQueryUseCase(PaymentDashQueryGateway gateway) {
        this.gateway = gateway;
    }

    public PaymentDashResponseDTO getAllDashboardData(int month, int year) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59);
        return gateway.getPaymentDashData(start, end);
    }
}
