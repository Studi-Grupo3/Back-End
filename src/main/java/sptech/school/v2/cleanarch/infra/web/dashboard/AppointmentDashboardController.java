package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
// ... (outros imports)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.v2.cleanarch.core.application.facades.dashboard.appointment.AppointmentDashFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@RestController
@RequestMapping("dashboard/appointments")
public class AppointmentDashboardController {

    private final AppointmentDashFacadeContract dashFacade;

    public AppointmentDashboardController(AppointmentDashFacadeContract dashFacade) {
        this.dashFacade = dashFacade;
    }

    @GetMapping
    @Operation(summary = "Lista os dados calculados para a dashboard", description = "Retorna todos os dados calculados para a dashboard de agendamentos, filtrando por período.")
    // ... (ApiResponses) ...
    public ResponseEntity<AppointmentDashResponseDTO> getAppointmentDashboard(
            @RequestParam(required = false) LocalDateTime start,
            @RequestParam(required = false) LocalDateTime end
    ) {
        if (start == null || end == null) {
            LocalDate today = LocalDate.now();
            start = today.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
            end = today.with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);
        }

        AppointmentDashResponseDTO data = dashFacade.getAppointmentDashData();

        return ResponseEntity.ok(data);
    }
}