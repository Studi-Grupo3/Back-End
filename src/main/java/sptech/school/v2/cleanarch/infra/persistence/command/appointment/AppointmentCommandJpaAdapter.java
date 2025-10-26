package sptech.school.v2.cleanarch.infra.persistence.command.appointment;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.appointment.AppointmentCommandGateway;
import sptech.school.domain.entity.Appointment;
import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;

@Component
public class AppointmentCommandJpaAdapter implements AppointmentCommandGateway {

    private final AppointmentJpaRepository repository;

    public AppointmentCommandJpaAdapter(AppointmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Appointment save(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
