// src/main/java/sptech/school/domain/dto/request/ConfirmPasswordRequestDTO.java
package sptech.school.domain.dto.request;

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
