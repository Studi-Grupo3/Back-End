package sptech.school.v2.cleanarch.infra.web.dashboard;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.application.facades.dashboard.adminsettings.AdminSettingsFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings.AdminSettingsRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.ConfirmPasswordRequestDTO;

@RestController
@RequestMapping("/settings/admin")
public class AdminSettingsController {

    private final AdminSettingsFacadeContract facade;

    public AdminSettingsController(AdminSettingsFacadeContract facade) {
        this.facade = facade;
    }

    @GetMapping
    public ResponseEntity<AdminSettingsResponseDTO> get() {
        return ResponseEntity.ok(facade.getSettings());
    }

    @PutMapping
    public ResponseEntity<AdminSettingsResponseDTO> put(@RequestBody @Valid AdminSettingsRequestDTO dto) {
        return ResponseEntity.ok(facade.updateSettings(dto));
    }

    @PatchMapping
    public ResponseEntity<AdminSettingsResponseDTO> patch(@RequestBody AdminSettingsRequestDTO dto) {
        return ResponseEntity.ok(facade.patchSettings(dto));
    }

    @PostMapping("/confirm-password")
    public ResponseEntity<Void> confirmPassword(
            @RequestBody ConfirmPasswordRequestDTO dto
    ) {
        boolean ok = facade.checkCurrentPassword(dto.getCurrentPassword());
        return ok
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
