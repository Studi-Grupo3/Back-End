package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.application.service.AdminSettingsService;
import sptech.school.domain.dto.request.AdminSettingsRequestDTO;
import sptech.school.domain.dto.request.ConfirmPasswordRequestDTO;
import sptech.school.domain.dto.response.AdminSettingsResponseDTO;

@RestController
@RequestMapping("/settings/admin")
public class AdminSettingsController {

    private final AdminSettingsService service;

    public AdminSettingsController(AdminSettingsService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Retorna as configurações administrativas", description = "Recupera as configurações atuais do administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AdminSettingsResponseDTO> get() {
        return ResponseEntity.ok(service.getSettings());
    }

    @PutMapping
    @Operation(summary = "Atualiza as configurações administrativas (substitui)", description = "Atualiza todas as configurações administrativas com os valores informados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações atualizadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AdminSettingsResponseDTO> put(@RequestBody AdminSettingsRequestDTO dto) {
        return ResponseEntity.ok(service.updateSettings(dto));
    }

    @PatchMapping
    @Operation(summary = "Atualiza parcialmente as configurações administrativas", description = "Aplica alterações parciais nas configurações do administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações parcialmente atualizadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AdminSettingsResponseDTO> patch(@RequestBody AdminSettingsRequestDTO dto) {
        return ResponseEntity.ok(service.patchSettings(dto));
    }

    @PostMapping("/confirm-password")
    @Operation(summary = "Confirma a senha atual do administrador", description = "Verifica se a senha atual fornecida corresponde à senha do administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha confirmada"),
            @ApiResponse(responseCode = "401", description = "Senha incorreta / não autorizada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Void> confirmPassword(
            @RequestBody ConfirmPasswordRequestDTO dto
    ) {
        boolean ok = service.checkCurrentPassword(dto.getCurrentPassword());
        return ok
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
