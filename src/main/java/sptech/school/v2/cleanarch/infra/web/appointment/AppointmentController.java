package sptech.school.v2.cleanarch.infra.web.appointment;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.application.facades.appointment.AppointmentFacadeContract;
import sptech.school.domain.dto.AppointmentDTO;
import sptech.school.domain.dto.request.AppointmentStatusDTO;
import sptech.school.domain.dto.response.AppointmentResponseDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.AppointmentStatus;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentFacadeContract facade;

    public AppointmentController(AppointmentFacadeContract facade) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody @Valid AppointmentDTO dto) {
        return ResponseEntity.ok(facade.createAppointment(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        return ResponseEntity.ok(facade.getAppointmentById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> listAppointments() {
        return ResponseEntity.ok(facade.listAllAppointments());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointments(@RequestBody @Valid AppointmentDTO dto, @PathVariable Integer id) {
        return ResponseEntity.ok(facade.updateAppointment(dto, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> patchStatus(
            @PathVariable Integer id,
            @RequestBody @Valid AppointmentStatusDTO statusDto
    ) {
        return ResponseEntity.ok(facade.patchStatus(id, statusDto.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {
        facade.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AppointmentResponseDTO>> listByTeacher(
            @PathVariable Integer teacherId,
            @RequestParam(required = false) AppointmentStatus status
    ) {
        return ResponseEntity.ok(facade.listByTeacher(teacherId, status));
    }
}
