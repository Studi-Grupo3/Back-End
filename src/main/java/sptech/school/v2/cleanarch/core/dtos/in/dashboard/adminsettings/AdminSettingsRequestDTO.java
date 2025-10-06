package sptech.school.v2.cleanarch.core.dtos.in.dashboard.adminsettings;

public class AdminSettingsRequestDTO {

    private String email;
    private String currentPassword;
    private String newPassword;
    private Boolean notifyPayments;
    private Boolean notifyAppointments;
    private Boolean notifyCancellations;

    public AdminSettingsRequestDTO() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Boolean getNotifyPayments() {
        return notifyPayments;
    }

    public void setNotifyPayments(Boolean notifyPayments) {
        this.notifyPayments = notifyPayments;
    }

    public Boolean getNotifyAppointments() {
        return notifyAppointments;
    }

    public void setNotifyAppointments(Boolean notifyAppointments) {
        this.notifyAppointments = notifyAppointments;
    }

    public Boolean getNotifyCancellations() {
        return notifyCancellations;
    }

    public void setNotifyCancellations(Boolean notifyCancellations) {
        this.notifyCancellations = notifyCancellations;
    }
}
