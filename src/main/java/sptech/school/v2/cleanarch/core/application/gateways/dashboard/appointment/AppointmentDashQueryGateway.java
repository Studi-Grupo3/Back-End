package sptech.school.v2.cleanarch.core.application.gateways.dashboard.appointment;

import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;

import java.time.LocalDateTime;

public interface AppointmentDashQueryGateway {
   AppointmentDashResponseDTO getAppointmentDashData(LocalDateTime start, LocalDateTime end);
}