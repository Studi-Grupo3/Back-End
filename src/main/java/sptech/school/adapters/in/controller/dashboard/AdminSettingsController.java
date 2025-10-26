//package sptech.school.adapters.in.controller.dashboard;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sptech.school.application.service.AdminSettingsService;
//import sptech.school.domain.dto.request.AdminSettingsRequestDTO;
//import sptech.school.domain.dto.request.ConfirmPasswordRequestDTO;
//import sptech.school.domain.dto.response.AdminSettingsResponseDTO;
//
//@RestController
//@RequestMapping("/settings/admin")
//public class AdminSettingsController {
//
//    private final AdminSettingsService service;
//
//    public AdminSettingsController(AdminSettingsService service) {
//        this.service = service;
//    }
//
//    @GetMapping
//    public ResponseEntity<AdminSettingsResponseDTO> get() {
//        return ResponseEntity.ok(service.getSettings());
//    }
//
//    @PutMapping
//    public ResponseEntity<AdminSettingsResponseDTO> put(@RequestBody AdminSettingsRequestDTO dto) {
//        return ResponseEntity.ok(service.updateSettings(dto));
//    }
//
//    @PatchMapping
//    public ResponseEntity<AdminSettingsResponseDTO> patch(@RequestBody AdminSettingsRequestDTO dto) {
//        return ResponseEntity.ok(service.patchSettings(dto));
//    }
//
//    @PostMapping("/confirm-password")
//    public ResponseEntity<Void> confirmPassword(
//            @RequestBody ConfirmPasswordRequestDTO dto
//    ) {
//        boolean ok = service.checkCurrentPassword(dto.getCurrentPassword());
//        return ok
//                ? ResponseEntity.ok().build()
//                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }
//}
//
