package sptech.school.adapters.in.controller.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.application.service.dashboard.OverviewDashService;
import sptech.school.domain.dto.response.dashboard.overview.OverviewDashDTO;

@RequestMapping("dashboards/overview")
@RestController
public class OverviewDashController {

    private final OverviewDashService dashboardService;

    public OverviewDashController(OverviewDashService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<OverviewDashDTO> getAllDashboardData() {
        OverviewDashDTO dto = dashboardService.getDashboardData();
        return ResponseEntity.ok(dto);
    }
}
