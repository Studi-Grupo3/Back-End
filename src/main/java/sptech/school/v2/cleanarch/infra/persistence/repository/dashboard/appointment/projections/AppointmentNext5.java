package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections;

import sptech.school.domain.enumerated.AppointmentStatus;

import java.time.LocalDateTime;

public interface AppointmentNext5 {
    String getStudentName();
    String getTeacherName();
    LocalDateTime getDateTime();
    Double getDuration();
    String getLocation();
    AppointmentStatus getStatus();
}
