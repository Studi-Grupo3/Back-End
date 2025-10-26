package sptech.school.v2.cleanarch.core.application.gateways.appointment;

import sptech.school.domain.entity.Appointment;

public interface AppointmentCommandGateway {
    Appointment save(Appointment appointment);
    void deleteById(Integer id);
}
