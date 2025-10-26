package sptech.school.v2.cleanarch.core.application.facades.appointment;

import sptech.school.domain.dto.AppointmentDTO;
import sptech.school.domain.dto.response.AppointmentResponseDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.AppointmentStatus;

import java.util.List;

public interface AppointmentFacadeContract {
    AppointmentResponseDTO createAppointment(AppointmentDTO dto);
    Appointment getAppointmentById(Integer id);
    List<AppointmentResponseDTO> listAllAppointments();
    Appointment updateAppointment(AppointmentDTO dto, Integer id);
    AppointmentResponseDTO patchStatus(Integer id, AppointmentStatus status);
    void deleteAppointment(Integer id);
    List<AppointmentResponseDTO> listByTeacher(Integer teacherId, AppointmentStatus status);
}
