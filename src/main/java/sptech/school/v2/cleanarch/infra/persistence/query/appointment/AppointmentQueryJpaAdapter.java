package sptech.school.v2.cleanarch.infra.persistence.query.appointment;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.appointment.AppointmentQueryGateway;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class AppointmentQueryJpaAdapter implements AppointmentQueryGateway {

    private final AppointmentJpaRepository repository;

    public AppointmentQueryJpaAdapter(AppointmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Appointment> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Appointment> findByStudentId(Integer studentId) {
        return repository.findByStudentId(studentId);
    }

    @Override
    public List<Appointment> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Appointment> findByTeacherId(Integer teacherId) {
        return repository.findByTeacherId(teacherId);
    }

    @Override
    public List<Appointment> findByTeacherIdAndStatus(Integer teacherId, AppointmentStatus status) {
        return repository.findByTeacherIdAndStatus(teacherId, status);
    }

    @Override
    public boolean existsByStudentIdAndTeacherIdAndDateTime(Integer studentId, Integer teacherId, LocalDateTime dateTime) {
        return repository.existsByStudentIdAndTeacherIdAndDateTime(studentId, teacherId, dateTime);
    }

    @Override
    public boolean existsByStudentIdAndTeacherIdAndDateTimeExceptId(Integer studentId, Integer teacherId, LocalDateTime dateTime, Integer exceptId) {
        return repository.existsByStudentIdAndTeacherIdAndDateTimeAndIdNot(studentId, teacherId, dateTime, exceptId);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
