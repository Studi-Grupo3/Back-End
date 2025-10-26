package sptech.school.v2.cleanarch.core.dtos.out.dashboard.adminsettings;

public class ConfirmPasswordRequestDTO {
    private String currentPassword;

    public ConfirmPasswordRequestDTO() {}

    public ConfirmPasswordRequestDTO(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
}
