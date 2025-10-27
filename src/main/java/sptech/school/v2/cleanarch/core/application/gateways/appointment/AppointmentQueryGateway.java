package sptech.school.v2.cleanarch.core.application.gateways.appointment;

import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentQueryGateway {
    Optional<Appointment> findById(Integer id);
    List<Appointment> findAll();
    List<Appointment> findByTeacherId(Integer teacherId);
    List<Appointment> findByTeacherIdAndStatus(Integer teacherId, AppointmentStatus status);
    boolean existsByStudentIdAndTeacherIdAndDateTime(Integer studentId, Integer teacherId, LocalDateTime dateTime);
    boolean existsByStudentIdAndTeacherIdAndDateTimeExceptId(Integer studentId, Integer teacherId, LocalDateTime dateTime, Integer exceptId);
    boolean existsById(Integer id);
}