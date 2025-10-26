package sptech.school.v2.cleanarch.core.application.usecases.command.appointment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import sptech.school.v2.cleanarch.core.application.gateways.appointment.AppointmentCommandGateway;
import sptech.school.v2.cleanarch.core.application.gateways.appointment.AppointmentQueryGateway;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.domain.dto.AppointmentDTO;
import sptech.school.domain.dto.response.AppointmentResponseDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.AppointmentStatus;
import sptech.school.v2.cleanarch.core.application.mappers.appointment.AppointmentMapper;

@Service
public class AppointmentCommandUseCase {

    private final AppointmentCommandGateway commandGateway;
    private final AppointmentQueryGateway queryGateway;
    private final StudentQueryGateway studentGateway;
    private final TeacherQueryGateway teacherGateway;
    private final AppointmentMapper mapper;

    public AppointmentCommandUseCase(AppointmentCommandGateway commandGateway,
                                     AppointmentQueryGateway queryGateway,
                                     StudentQueryGateway studentGateway,
                                     TeacherQueryGateway teacherGateway,
                                     AppointmentMapper mapper) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.studentGateway = studentGateway;
        this.teacherGateway = teacherGateway;
        this.mapper = mapper;
    }

    @Transactional
    public AppointmentResponseDTO create(AppointmentDTO dto) {
        Student student = studentGateway.findById(dto.idStudent());
        Teacher teacher = teacherGateway.findById(dto.idTeacher());

        if (student == null || teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student or Teacher not found.");
        }

        if (queryGateway.existsByStudentIdAndTeacherIdAndDateTime(dto.idStudent(), dto.idTeacher(), dto.dateTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users already have an appointment at this time.");
        }

        Appointment appointment = mapper.toEntity(dto);

        appointment.setStudent(student);
        appointment.setTeacher(teacher);

        Appointment saved = commandGateway.save(appointment);

        return mapper.toResponseDto(saved);
    }

    @Transactional
    public Appointment update(AppointmentDTO dto, Integer id) {
        Appointment appointment = queryGateway.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));

        if (dto.dateTime() != null &&
                queryGateway.existsByStudentIdAndTeacherIdAndDateTimeExceptId(dto.idStudent(), dto.idTeacher(), dto.dateTime(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users already have an appointment at this time.");
        }

        mapper.updateFromDto(dto, appointment);

        Student student = studentGateway.findById(dto.idStudent());
        Teacher teacher = teacherGateway.findById(dto.idTeacher());
        if (student == null || teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student or Teacher not found.");
        }
        appointment.setStudent(student);
        appointment.setTeacher(teacher);

        return commandGateway.save(appointment);
    }

    @Transactional
    public AppointmentResponseDTO patchStatus(Integer id, AppointmentStatus newStatus) {
        Appointment appointment = queryGateway.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));
        appointment.setStatus(newStatus);
        Appointment saved = commandGateway.save(appointment);
        return mapper.toResponseDto(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!queryGateway.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found.");
        }
        commandGateway.deleteById(id);
    }
}
