package sptech.school.v2.cleanarch.infra.web.appointment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.application.facades.appointment.AppointmentFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.in.AppointmentStatusDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.AppointmentDTO;
import sptech.school.v2.cleanarch.core.dtos.out.AppointmentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentFacadeContract facade;

    public AppointmentController(AppointmentFacadeContract facade) {
        this.facade = facade;
    }

    @PostMapping
    @Operation(summary = "Cria um novo agendamento",
            description = "Cria um agendamento para um estudante e professor em um horário específico. " +
                    "Verifica conflitos de horário e existência de estudante/professor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou conflito de horário"),
            @ApiResponse(responseCode = "404", description = "Student ou Teacher não encontrados")
    })
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody @Valid AppointmentDTO dto) {
        return ResponseEntity.ok(facade.createAppointment(dto));
    }

    @GetMapping("student/{studentId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByStudentId(
            @Parameter(description = "ID do student") @PathVariable Integer studentId
    ) {
        List<AppointmentResponseDTO> appointments = facade.getAppointmentById(studentId);

        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(appointments);
    }

    @GetMapping
    @Operation(summary = "Lista todos os agendamentos",
            description = "Retorna uma lista de agendamentos em formato de response DTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> listAppointments() {
        return ResponseEntity.ok(facade.listAllAppointments());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um agendamento (PUT)",
            description = "Substitui os campos do agendamento com base no DTO fornecido. " +
                    "Verifica conflitos de horário e existência de student/teacher.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou conflito de horário"),
            @ApiResponse(responseCode = "404", description = "Agendamento, Student ou Teacher não encontrados")
    })
    public ResponseEntity<Appointment> updateAppointments(
            @RequestBody @Valid AppointmentDTO dto,
            @Parameter(description = "ID do agendamento a ser atualizado") @PathVariable Integer id
    ) {
        return ResponseEntity.ok(facade.updateAppointment(dto, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza somente o status do agendamento (PATCH)",
            description = "Altera o status do agendamento (ex.: CONFIRMED, CANCELED).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<AppointmentResponseDTO> patchStatus(
            @Parameter(description = "ID do agendamento") @PathVariable Integer id,
            @RequestBody @Valid AppointmentStatusDTO statusDto
    ) {
        return ResponseEntity.ok(facade.patchStatus(id, statusDto.getStatus()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um agendamento",
            description = "Deleta um agendamento pelo id. Retorna 204 quando removido com sucesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    public ResponseEntity<Void> deleteAppointment(
            @Parameter(description = "ID do agendamento a ser removido") @PathVariable Integer id
    ) {
        facade.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Lista agendamentos por professor",
            description = "Retorna agendamentos filtrados por professor e opcionalmente por status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos do professor retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<List<AppointmentResponseDTO>> listByTeacher(
            @Parameter(description = "ID do professor") @PathVariable Integer teacherId,
            @Parameter(description = "Status opcional para filtrar (ex.: CONFIRMED)") @RequestParam(required = false) AppointmentStatus status
    ) {
        return ResponseEntity.ok(facade.listByTeacher(teacherId, status));
    }
}
