package sptech.school.v2.cleanarch.core.application.gateways.dashboard.overview;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview.OverviewDashResponseDTO;

import java.time.LocalDateTime;

public interface OverviewDashQueryGateway {
    OverviewDashResponseDTO getOverviewDashData(LocalDateTime start, LocalDateTime end, LocalDateTime yearStart);
}
