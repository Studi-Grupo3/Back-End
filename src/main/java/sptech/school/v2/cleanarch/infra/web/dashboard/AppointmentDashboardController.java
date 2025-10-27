package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.v2.cleanarch.core.application.facades.dashboard.appointment.AppointmentDashFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;



@RestController
@RequestMapping("/api/dashboard/appointments")
public class AppointmentDashboardController {

    private final AppointmentDashFacadeContract dashFacade;

    public AppointmentDashboardController(AppointmentDashFacadeContract dashFacade) {
        this.dashFacade = dashFacade;
    }

    @GetMapping
    @Operation(summary = "Lista os dados calculados para a dashboard", description = "Retorna todos os dados calculados para a dashboard de agendamentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados da dashboard retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AppointmentDashResponseDTO> getAppointmentDashboard() {
        AppointmentDashResponseDTO data = dashFacade.getAppointmentDashData();

        return ResponseEntity.status(201).body(data);
    }
}
