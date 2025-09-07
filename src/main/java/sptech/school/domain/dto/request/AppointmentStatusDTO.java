package sptech.school.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

public class AppointmentStatusDTO {

    @NotNull
    private AppointmentStatus status;

    public AppointmentStatusDTO() { }

    public AppointmentStatusDTO(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
