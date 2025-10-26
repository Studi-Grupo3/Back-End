package sptech.school.v2.cleanarch.core.application.gateways.appointment;

import sptech.school.v2.cleanarch.domain.entities.Appointment;

public interface AppointmentCommandGateway {
    Appointment save(Appointment appointment);
    void deleteById(Integer id);
}
