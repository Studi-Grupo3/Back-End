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

@Service
public class AdminSettingsCommandUseCase {

    private final AdminCommandGateway commandGateway;
    private final AdminQueryGateway queryGateway;
    private final AdminSettingsMapper mapper;
    private final BCryptPasswordEncoder encoder;

    public AdminSettingsCommandUseCase(AdminCommandGateway commandGateway,
                                       AdminQueryGateway queryGateway,
                                       AdminSettingsMapper mapper,
                                       BCryptPasswordEncoder encoder) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    @Transactional
    public AdminSettingsResponseDTO updateSettings(AdminSettingsRequestDTO dto) {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));

        mapper.overwriteFromDto(dto, admin);

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            admin.setPassword(encoder.encode(dto.getNewPassword()));
        }

        Admin saved = commandGateway.save(admin);
        return mapper.toResponse(saved);
    }

    @Transactional
    public AdminSettingsResponseDTO patchSettings(AdminSettingsRequestDTO dto) {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));

        mapper.updateFromDtoIgnoreNull(dto, admin);

        if (dto.getCurrentPassword() != null && dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            admin.setPassword(encoder.encode(dto.getNewPassword()));
        }
        Admin saved = commandGateway.save(admin);
        return mapper.toResponse(saved);
    }

    public boolean checkCurrentPassword(String rawPassword) {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));
        return encoder.matches(rawPassword, admin.getPassword());
    }
}
