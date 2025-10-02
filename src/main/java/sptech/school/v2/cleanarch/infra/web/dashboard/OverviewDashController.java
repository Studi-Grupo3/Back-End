package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Recupera os dados do dashboard de overview", description = "Retorna os dados agregados exibidos no painel de overview.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do dashboard retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<OverviewDashDTO> getAllDashboardData() {
        OverviewDashDTO dto = dashboardService.getDashboardData();
        return ResponseEntity.ok(dto);
    }
}
