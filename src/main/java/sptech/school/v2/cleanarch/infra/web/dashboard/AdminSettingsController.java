package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.application.facades.dashboard.adminsettings.AdminSettingsFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings.AdminSettingsRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.ConfirmPasswordRequestDTO;

@RequestMapping("/settings/admin")
@RestController
public class AdminSettingsController {

    private final AdminSettingsFacadeContract facade;

    public AdminSettingsController(AdminSettingsFacadeContract facade) {
        this.facade = facade;
    }

    @GetMapping
    @Operation(summary = "Recupera as configurações do admin",
            description = "Retorna as configurações administrativas atuais (email e preferências de notificação).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Configurações do admin não encontradas")
    })
    public ResponseEntity<AdminSettingsResponseDTO> get() {
        return ResponseEntity.ok(facade.getSettings());
    }

    @PutMapping
    @Operation(summary = "Substitui completamente as configurações do admin",
            description = "PUT que sobrescreve todos os campos das configurações administrativas. " +
                    "Campos não enviados podem ser considerados nulos e sobrescrever valores existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações atualizadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AdminSettingsResponseDTO> put(@RequestBody @Valid AdminSettingsRequestDTO dto) {
        return ResponseEntity.ok(facade.updateSettings(dto));
    }

    @PatchMapping
    @Operation(summary = "Atualiza parcialmente as configurações do admin",
            description = "PATCH que altera somente os campos enviados no corpo da requisição. " +
                    "Útil para alterações parciais como habilitar/desabilitar notificações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Configurações parciais aplicadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<AdminSettingsResponseDTO> patch(@RequestBody AdminSettingsRequestDTO dto) {
        return ResponseEntity.ok(facade.patchSettings(dto));
    }

    @PostMapping("/confirm-password")
    @Operation(summary = "Confirma a senha atual do admin",
            description = "Verifica se a senha enviada corresponde à senha atual do admin. Retorna 200 se válida ou 401 se inválida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha confirmada"),
            @ApiResponse(responseCode = "401", description = "Senha incorreta")
    })
    public ResponseEntity<Void> confirmPassword(
            @RequestBody ConfirmPasswordRequestDTO dto
    ) {
        boolean ok = facade.checkCurrentPassword(dto.getCurrentPassword());
        return ok
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
