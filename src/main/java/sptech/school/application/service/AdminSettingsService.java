package sptech.school.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.adapters.out.persistence.AdminRepository;
import sptech.school.domain.dto.request.AdminSettingsRequestDTO;
import sptech.school.domain.dto.response.AdminSettingsResponseDTO;
import sptech.school.domain.entity.Admin;

@Service
public class AdminSettingsService {

    private final AdminRepository repository;
    private final BCryptPasswordEncoder encoder;

    public AdminSettingsService(AdminRepository repository,
                                BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public AdminSettingsResponseDTO getSettings() {
        Admin admin = repository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin settings not found"));
        return toResponse(admin);
    }

    public AdminSettingsResponseDTO updateSettings(AdminSettingsRequestDTO dto) {
        // PUT completo — sobrescreve todos os campos
        Admin admin = findAdmin();
        applyAllFields(admin, dto);
        repository.save(admin);
        return toResponse(admin);
    }

    public AdminSettingsResponseDTO patchSettings(AdminSettingsRequestDTO dto) {
        Admin admin = findAdmin();
        if (dto.getEmail() != null) {
            admin.setEmail(dto.getEmail());
        }
        if (dto.getCurrentPassword() != null && dto.getNewPassword() != null) {
            // aqui você checaria a senha atual antes de setar a nova
            admin.setPassword(encoder.encode(dto.getNewPassword()));
        }
        if (dto.getNotifyPayments() != null) {
            admin.setNotifyPayments(dto.getNotifyPayments());
        }
        if (dto.getNotifyAppointments() != null) {
            admin.setNotifyAppointments(dto.getNotifyAppointments());
        }
        if (dto.getNotifyCancellations() != null) {
            admin.setNotifyCancellations(dto.getNotifyCancellations());
        }
        repository.save(admin);
        return toResponse(admin);
    }

    public boolean checkCurrentPassword(String rawPassword) {
        Admin admin = findAdmin();
        return encoder.matches(rawPassword, admin.getPassword());
    }

    private Admin findAdmin() {
        return repository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin settings not found"));
    }

    private void applyAllFields(Admin admin, AdminSettingsRequestDTO dto) {
        admin.setEmail(dto.getEmail());
        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            admin.setPassword(encoder.encode(dto.getNewPassword()));
        }
        admin.setNotifyPayments(dto.getNotifyPayments());
        admin.setNotifyAppointments(dto.getNotifyAppointments());
        admin.setNotifyCancellations(dto.getNotifyCancellations());
    }

    private AdminSettingsResponseDTO toResponse(Admin admin) {
        AdminSettingsResponseDTO resp = new AdminSettingsResponseDTO();
        resp.setEmail(admin.getEmail());
        resp.setNotifyPayments(admin.getNotifyPayments());
        resp.setNotifyAppointments(admin.getNotifyAppointments());
        resp.setNotifyCancellations(admin.getNotifyCancellations());
        return resp;
    }
}

