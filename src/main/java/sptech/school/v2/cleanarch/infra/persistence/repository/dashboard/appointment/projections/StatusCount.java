package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections;

import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

public interface StatusCount {
    AppointmentStatus getStatus();
    Long getTotal();
}
