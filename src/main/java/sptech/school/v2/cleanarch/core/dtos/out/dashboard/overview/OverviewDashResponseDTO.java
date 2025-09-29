package sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview;

import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartBarDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.ChartLineDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.overview.OverviewStatsDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.dashboard.overview.OverviewTableDTO;

import java.util.List;

public record OverviewDashResponseDTO(
        OverviewStatsDTO stats,
        List<ChartLineDTO> monthlyRevenue,
        List<ChartBarDTO> lessonsPerDay,
        List<OverviewTableDTO> recentPayments
) {}
