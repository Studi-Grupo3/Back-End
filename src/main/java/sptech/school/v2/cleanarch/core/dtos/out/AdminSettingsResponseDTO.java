package sptech.school.v2.cleanarch.core.dtos.out;

public class AdminSettingsResponseDTO {

    private String email;
    private Boolean notifyPayments;
    private Boolean notifyAppointments;
    private Boolean notifyCancellations;

    public AdminSettingsResponseDTO() {}

    public AdminSettingsResponseDTO(String email, Boolean notifyPayments, Boolean notifyAppointments, Boolean notifyCancellations) {
        this.email = email;
        this.notifyPayments = notifyPayments;
        this.notifyAppointments = notifyAppointments;
        this.notifyCancellations = notifyCancellations;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Boolean getNotifyPayments() { return notifyPayments; }

    public void setNotifyPayments(Boolean notifyPayments) { this.notifyPayments = notifyPayments; }

    public Boolean getNotifyAppointments() { return notifyAppointments; }

    public void setNotifyAppointments(Boolean notifyAppointments) { this.notifyAppointments = notifyAppointments; }

    public Boolean getNotifyCancellations() { return notifyCancellations; }

    public void setNotifyCancellations(Boolean notifyCancellations) { this.notifyCancellations = notifyCancellations; }
}
