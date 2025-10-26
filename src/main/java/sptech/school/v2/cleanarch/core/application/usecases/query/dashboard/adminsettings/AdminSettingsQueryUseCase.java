package sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.adminsettings;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.domain.entities.Admin;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings.AdminQueryGateway;
import sptech.school.v2.cleanarch.core.application.mappers.dashboard.adminsettings.AdminSettingsMapper;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;

@Service
public class AdminSettingsQueryUseCase {

    private final AdminQueryGateway queryGateway;
    private final AdminSettingsMapper mapper;

    public AdminSettingsQueryUseCase(AdminQueryGateway queryGateway, AdminSettingsMapper mapper) {
        this.queryGateway = queryGateway;
        this.mapper = mapper;
    }

    public AdminSettingsResponseDTO getSettings() {
        Admin admin = queryGateway.findFirstAdmin()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin settings not found"));
        return mapper.toResponse(admin);
    }
}
