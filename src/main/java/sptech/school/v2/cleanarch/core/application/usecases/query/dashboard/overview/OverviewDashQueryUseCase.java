package sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.overview;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.overview.OverviewDashQueryGateway;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview.OverviewDashResponseDTO;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

@Service
public class OverviewDashQueryUseCase {
    private final OverviewDashQueryGateway gateway;

    public OverviewDashQueryUseCase(OverviewDashQueryGateway gateway) {
        this.gateway = gateway;
    }

    public OverviewDashResponseDTO getAllDashboardData() {
        YearMonth now = YearMonth.now(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime start = now.atDay(1).atStartOfDay();
        LocalDateTime end = now.atEndOfMonth().atTime(23, 59, 59);

        // For monthly revenue chart, use year-to-date range
        LocalDateTime yearStart = now.withMonth(1).atDay(1).atStartOfDay();

        return gateway.getOverviewDashData(start, end, yearStart);
    }
}
