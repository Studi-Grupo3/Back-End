package sptech.school.v2.cleanarch.core.application.facades.dashboard.adminsettings;

import sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings.AdminSettingsRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings.AdminSettingsResponseDTO;

public interface AdminSettingsFacadeContract {
    AdminSettingsResponseDTO getSettings();
    AdminSettingsResponseDTO updateSettings(AdminSettingsRequestDTO dto);
    AdminSettingsResponseDTO patchSettings(AdminSettingsRequestDTO dto);
    boolean checkCurrentPassword(String rawPassword);
}
