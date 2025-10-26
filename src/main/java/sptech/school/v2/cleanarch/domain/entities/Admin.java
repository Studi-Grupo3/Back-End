package sptech.school.v2.cleanarch.domain.entities;

import jakarta.persistence.*;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Boolean notifyPayments;
    private Boolean notifyAppointments;
    private Boolean notifyCancellations;

    public Admin() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Boolean getNotifyPayments() { return notifyPayments; }

    public void setNotifyPayments(Boolean notifyPayments) { this.notifyPayments = notifyPayments; }

    public Boolean getNotifyAppointments() { return notifyAppointments; }

    public void setNotifyAppointments(Boolean notifyAppointments) { this.notifyAppointments = notifyAppointments; }

    public Boolean getNotifyCancellations() { return notifyCancellations; }

    public void setNotifyCancellations(Boolean notifyCancellations) { this.notifyCancellations = notifyCancellations; }
}
