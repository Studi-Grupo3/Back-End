package sptech.school.v2.cleanarch.core.application.facades.dashboard.appointment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.query.dashboard.appointment.AppointmentDashQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.appointment.AppointmentDashResponseDTO;

@Service
public class AppointmentDashFacade implements AppointmentDashFacadeContract{

    private final AppointmentDashQueryUseCase appointmentDashQueryUseCase;

    public AppointmentDashFacade(AppointmentDashQueryUseCase appointmentDashQueryUseCase) {
        this.appointmentDashQueryUseCase = appointmentDashQueryUseCase;
    }

    @Override
    public AppointmentDashResponseDTO getAppointmentDashData() {
        return appointmentDashQueryUseCase.getAllDashboardData();
    }
}
