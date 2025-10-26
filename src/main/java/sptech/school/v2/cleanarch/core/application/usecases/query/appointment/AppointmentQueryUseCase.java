package sptech.school.v2.cleanarch.core.application.usecases.query.appointment;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.appointment.AppointmentQueryGateway;

import sptech.school.v2.cleanarch.core.dtos.out.AppointmentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import sptech.school.v2.cleanarch.core.application.mappers.appointment.AppointmentMapper;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.util.List;

@Service
public class AppointmentQueryUseCase {

    private final AppointmentQueryGateway queryGateway;
    private final AppointmentMapper mapper;

    public AppointmentQueryUseCase(AppointmentQueryGateway queryGateway, AppointmentMapper mapper) {
        this.queryGateway = queryGateway;
        this.mapper = mapper;
    }

    public Appointment findById(Integer id) {
        return queryGateway.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));
    }

    public List<AppointmentResponseDTO> listAll() {
        return queryGateway.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    public List<AppointmentResponseDTO> listByTeacher(Integer teacherId, AppointmentStatus status) {
        List<Appointment> appointments = (status != null)
                ? queryGateway.findByTeacherIdAndStatus(teacherId, status)
                : queryGateway.findByTeacherId(teacherId);

        return appointments.stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}
