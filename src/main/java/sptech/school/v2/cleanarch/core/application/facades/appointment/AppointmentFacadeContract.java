package sptech.school.v2.cleanarch.core.application.facades.appointment;

import sptech.school.v2.cleanarch.core.dtos.internal.AppointmentDTO;
import sptech.school.v2.cleanarch.core.dtos.out.AppointmentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.util.List;

public interface AppointmentFacadeContract {
    AppointmentResponseDTO createAppointment(AppointmentDTO dto);
    List<AppointmentResponseDTO> getAppointmentById(Integer id);
    List<AppointmentResponseDTO> listAllAppointments();
    Appointment updateAppointment(AppointmentDTO dto, Integer id);
    AppointmentResponseDTO patchStatus(Integer id, AppointmentStatus status);
    void deleteAppointment(Integer id);
    List<AppointmentResponseDTO> listByTeacher(Integer teacherId, AppointmentStatus status);
}
