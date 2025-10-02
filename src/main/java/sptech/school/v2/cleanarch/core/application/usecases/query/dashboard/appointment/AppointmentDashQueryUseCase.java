package sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.appointment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.appointment.AppointmentDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

@Service
public class AppointmentDashQueryUseCase {
    private final AppointmentDashQueryGateway gateway;

    public AppointmentDashQueryUseCase(AppointmentDashQueryGateway gateway) {
        this.gateway = gateway;
    }

    public AppointmentDashResponseDTO getAllDashboardData() {
        YearMonth now = YearMonth.now(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime start = now.atDay(1).atStartOfDay();
        LocalDateTime end = now.atEndOfMonth().atTime(23, 59, 59);

        return gateway.getAppointmentDashData(start, end);
    }
}
