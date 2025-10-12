package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.v2.cleanarch.core.application.facades.dashboard.teacher.TeacherDashFacadeContract;

@RestController
@RequestMapping("/dashboards")
public class TeacherDashController {

    private final TeacherDashFacadeContract facade;

    public TeacherDashController(TeacherDashFacadeContract facade) {
        this.facade = facade;
    }

    @GetMapping
    @Operation(summary = "Recupera dados do dashboard de professores", description = "Retorna as métricas e informações exibidas no painel de professores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do dashboard retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<TeacherDashboardDTO> getTeacherDashboard() {
        TeacherDashboardDTO dashboard = facade.getTeacherDashData();
        return ResponseEntity.ok(dashboard);
    }
}
