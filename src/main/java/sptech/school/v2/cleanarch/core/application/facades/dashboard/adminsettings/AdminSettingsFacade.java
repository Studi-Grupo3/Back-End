package sptech.school.v2.cleanarch.core.application.facades.dashboard.adminsettings;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.command.dashboard.adminsettings.AdminSettingsCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.adminsettings.AdminSettingsQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings.AdminSettingsRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;

@Service
public class AdminSettingsFacade implements AdminSettingsFacadeContract {

    private final AdminSettingsQueryUseCase queryUseCase;
    private final AdminSettingsCommandUseCase commandUseCase;

    public AdminSettingsFacade(AdminSettingsQueryUseCase queryUseCase,
                               AdminSettingsCommandUseCase commandUseCase) {
        this.queryUseCase = queryUseCase;
        this.commandUseCase = commandUseCase;
    }

    @Override
    public AdminSettingsResponseDTO getSettings() {
        return queryUseCase.getSettings();
    }

    @Override
    public AdminSettingsResponseDTO updateSettings(AdminSettingsRequestDTO dto) {
        return commandUseCase.updateSettings(dto);
    }

    @Override
    public AdminSettingsResponseDTO patchSettings(AdminSettingsRequestDTO dto) {
        return commandUseCase.patchSettings(dto);
    }

    @Override
    public boolean checkCurrentPassword(String rawPassword) {
        return commandUseCase.checkCurrentPassword(rawPassword);
    }
}
