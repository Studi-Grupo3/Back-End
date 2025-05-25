package sptech.school.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.domain.dto.AppointmentDTO;
import sptech.school.domain.dto.request.AppointmentStatusDTO;
import sptech.school.domain.dto.response.AppointmentResponseDTO;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.application.mappers.AppointmentMapper;
import sptech.school.adapters.out.persistence.AppointmentRepository;
import sptech.school.domain.enumerated.AppointmentStatus;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    public Appointment create(AppointmentDTO dto) {
        Student student = studentService.findById(dto.idStudent());
        Teacher teacher = teacherService.findById(dto.idTeacher());

        if (appointmentRepository.existsByStudentIdAndTeacherIdAndDateTime(dto.idStudent(), dto.idTeacher(), dto.dateTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users already have an appointment at this time.");
        }

        Appointment appointment = new Appointment(student, teacher, dto.dateTime(), dto.lessonDuration(), dto.location());
        return appointmentRepository.save(appointment);
    }

    public Appointment findById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));
    }

    public List<AppointmentResponseDTO> listAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toResponseDto)
                .toList();
    }

    public List<AppointmentResponseDTO> listAllResponse() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toResponseDto)
                .toList();
    }

    public Appointment update(AppointmentDTO dto, Integer id) {
        Appointment appointment = findById(id);

        if (appointmentRepository.existsByStudentIdAndTeacherIdAndDateTime(dto.idStudent(), dto.idTeacher(), dto.dateTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users already have an appointment at this time.");
        }

        if (dto.dateTime() != null) {
            appointment.setDateTime(dto.dateTime());
        }

        return appointmentRepository.save(appointment);
    }

    public Appointment patchStatus(Integer id, AppointmentStatus newStatus) {
        Appointment appointment = findById(id);
        appointment.setStatus(newStatus);
        return appointmentRepository.save(appointment);
    }

    public void delete(Integer id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found.");
        }
        appointmentRepository.deleteById(id);
    }
}
