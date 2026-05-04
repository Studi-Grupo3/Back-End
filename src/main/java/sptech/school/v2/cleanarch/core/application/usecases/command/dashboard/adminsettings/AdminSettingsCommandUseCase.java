package sptech.school.v2.cleanarch.core.application.usecases.command.dashboard.adminsettings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sptech.school.v2.cleanarch.domain.entities.Admin;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings.AdminCommandGateway;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings.AdminQueryGateway;
import sptech.school.v2.cleanarch.core.application.mappers.dashboard.adminsettings.AdminSettingsMapper;
import sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings.AdminSettingsRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;

@Service
public class AdminSettingsCommandUseCase {

    private final AdminCommandGateway commandGateway;
    private final AdminQueryGateway queryGateway;
    private final AdminSettingsMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final TeacherJpaRepository teacherRepository;

    public AdminSettingsCommandUseCase(AdminCommandGateway commandGateway,
                                       AdminQueryGateway queryGateway,
                                       AdminSettingsMapper mapper,
                                       BCryptPasswordEncoder encoder,
                                       TeacherJpaRepository teacherRepository) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.mapper = mapper;
        this.encoder = encoder;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public AdminSettingsResponseDTO updateSettings(AdminSettingsRequestDTO dto) {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));

        mapper.overwriteFromDto(dto, admin);

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            admin.setPassword(encoder.encode(dto.getNewPassword()));
            // Also update Teacher entity used for authentication
            teacherRepository.findByEmail(admin.getEmail()).ifPresent(teacher -> {
                teacher.setPassword(encoder.encode(dto.getNewPassword()));
                teacherRepository.save(teacher);
            });
        }

        Admin saved = commandGateway.save(admin);
        return mapper.toResponse(saved);
    }

    @Transactional
    public AdminSettingsResponseDTO patchSettings(AdminSettingsRequestDTO dto) {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));

        // Find the Teacher entity (used for authentication) by current email
        String currentEmail = admin.getEmail();

        // Verify current password before making any changes
        if (dto.getCurrentPassword() != null) {
            boolean valid = teacherRepository.findByEmail(currentEmail)
                    .map(t -> encoder.matches(dto.getCurrentPassword(), t.getPassword()))
                    .orElseGet(() -> encoder.matches(dto.getCurrentPassword(), admin.getPassword()));
            if (!valid) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Senha atual incorreta");
            }
        }

        mapper.updateFromDtoIgnoreNull(dto, admin);

        if (dto.getCurrentPassword() != null && dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            String encoded = encoder.encode(dto.getNewPassword());
            admin.setPassword(encoded);
            // Update Teacher entity password (used for JWT authentication)
            teacherRepository.findByEmail(currentEmail).ifPresent(teacher -> {
                teacher.setPassword(encoded);
                if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
                    teacher.setEmail(dto.getEmail());
                }
                teacherRepository.save(teacher);
            });
        } else if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            // Email-only change
            teacherRepository.findByEmail(currentEmail).ifPresent(teacher -> {
                teacher.setEmail(dto.getEmail());
                teacherRepository.save(teacher);
            });
        }

        Admin saved = commandGateway.save(admin);
        return mapper.toResponse(saved);
    }

    public boolean checkCurrentPassword(String rawPassword) {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));
        // Check against Teacher entity password (used for authentication)
        return teacherRepository.findByEmail(admin.getEmail())
                .map(teacher -> encoder.matches(rawPassword, teacher.getPassword()))
                .orElseGet(() -> encoder.matches(rawPassword, admin.getPassword()));
    }
}
