package sptech.school.v2.cleanarch.core.application.facades.dashboard.overview;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.overview.OverviewDashQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview.OverviewDashResponseDTO;

@Service
public class OverviewDashFacade implements OverviewDashFacadeContract {

    private final OverviewDashQueryUseCase overviewDashQueryUseCase;

    public OverviewDashFacade(OverviewDashQueryUseCase overviewDashQueryUseCase) {
        this.overviewDashQueryUseCase = overviewDashQueryUseCase;
    }

    @Override
    public OverviewDashResponseDTO getOverviewDashData() {
        return overviewDashQueryUseCase.getAllDashboardData();
    }
}
