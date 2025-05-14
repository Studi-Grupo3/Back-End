package sptech.school.adapters.in.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.application.service.dashboard.AppointmentDashService;
import sptech.school.domain.dto.response.dashboard.appointment.AppointmentDashDTO;

@RestController
@RequestMapping("/dashboard/appointments")
public class AppointmentDashController {

    @Autowired
    private AppointmentDashService dashService;

    @GetMapping
    public ResponseEntity<AppointmentDashDTO> getAppointmentDashboard() {
        AppointmentDashDTO dto = dashService.getDashboardData();
        return ResponseEntity.ok(dto);
    }
}
