package sptech.school.v2.cleanarch.core.application.facades.appointment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.command.appointment.AppointmentCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.appointment.AppointmentQueryUseCase;
import sptech.school.v2.cleanarch.core.dtos.internal.AppointmentDTO;
import sptech.school.v2.cleanarch.core.dtos.out.appointment.AppointmentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.util.List;

@Service
public class AppointmentFacade implements AppointmentFacadeContract {

    private final AppointmentCommandUseCase commandUseCase;
    private final AppointmentQueryUseCase queryUseCase;

    public AppointmentFacade(AppointmentCommandUseCase commandUseCase,
                             AppointmentQueryUseCase queryUseCase) {
        this.commandUseCase = commandUseCase;
        this.queryUseCase = queryUseCase;
    }

    @Override
    public AppointmentResponseDTO createAppointment(AppointmentDTO dto) {
        return commandUseCase.create(dto);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentById(Integer id) {
        return queryUseCase.findByStudentId(id);
    }

    @Override
    public List<AppointmentResponseDTO> listAllAppointments() {
        return queryUseCase.listAll();
    }

    @Override
    public Appointment updateAppointment(AppointmentDTO dto, Integer id) {
        return commandUseCase.update(dto, id);
    }

    @Override
    public AppointmentResponseDTO patchStatus(Integer id, AppointmentStatus status) {
        return commandUseCase.patchStatus(id, status);
    }

    @Override
    public void deleteAppointment(Integer id) {
        commandUseCase.delete(id);
    }

    @Override
    public List<AppointmentResponseDTO> listByTeacher(Integer teacherId, AppointmentStatus status) {
        return queryUseCase.listByTeacher(teacherId, status);
    }

    public AppointmentResponseDTO findAppointmentById(Integer id) {
        return queryUseCase.findById(id);
    }
}
