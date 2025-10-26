//package sptech.school.application.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//import sptech.school.domain.dto.AppointmentDTO;
//import sptech.school.domain.dto.response.AppointmentResponseDTO;
//import sptech.school.domain.entity.Appointment;
//import sptech.school.domain.entity.Student;
//import sptech.school.domain.entity.Teacher;
//import sptech.school.application.mappers.AppointmentMapper;
//import sptech.school.adapters.out.persistence.AppointmentRepository;
//import sptech.school.domain.enumerated.AppointmentStatus;
//
//import java.util.List;
//
//@Service
//public class AppointmentService {
//    private AppointmentRepository appointmentRepository;
//
//    private AppointmentMapper appointmentMapper;
//
//    private TeacherService teacherService;
//
//    private StudentService studentService;
//
//    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, TeacherService teacherService, StudentService studentService) {
//        this.appointmentRepository = appointmentRepository;
//        this.appointmentMapper = appointmentMapper;
//        this.teacherService = teacherService;
//        this.studentService = studentService;
//    }
//
//    public AppointmentResponseDTO create(AppointmentDTO dto) {
//        Appointment appointment = appointmentMapper.toEntity(dto);
//
//        Student student = studentService.findById(dto.idStudent());
//        Teacher teacher = teacherService.findById(dto.idTeacher());
//        appointment.setStudent(student);
//        appointment.setTeacher(teacher);
//
//        if (appointmentRepository.existsByStudentIdAndTeacherIdAndDateTime(
//                dto.idStudent(), dto.idTeacher(), dto.dateTime())) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Users already have an appointment at this time."
//            );
//        }
//
//        Appointment saved = appointmentRepository.save(appointment);
//
//        return appointmentMapper.toResponseDto(saved);
//    }
//
//    public Appointment findById(Integer id) {
//        return appointmentRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found."));
//    }
//
//    public List<AppointmentResponseDTO> listAll() {
//        return appointmentRepository.findAll()
//                .stream()
//                .map(appointmentMapper::toResponseDto)
//                .toList();
//    }
//
//    public List<AppointmentResponseDTO> listAllResponse() {
//        return appointmentRepository.findAll()
//                .stream()
//                .map(appointmentMapper::toResponseDto)
//                .toList();
//    }
//
//    public Appointment update(AppointmentDTO dto, Integer id) {
//        Appointment appointment = findById(id);
//
//        if (appointmentRepository.existsByStudentIdAndTeacherIdAndDateTime(dto.idStudent(), dto.idTeacher(), dto.dateTime())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Users already have an appointment at this time.");
//        }
//
//        if (dto.dateTime() != null) {
//            appointment.setDateTime(dto.dateTime());
//        }
//
//        return appointmentRepository.save(appointment);
//    }
//
//    public List<AppointmentResponseDTO> listByTeacher(Integer teacherId, AppointmentStatus status) {
//        List<Appointment> appointments;
//
//        if (status != null) {
//            appointments = appointmentRepository.findByTeacherIdAndStatus(teacherId, status);
//        } else {
//            appointments = appointmentRepository.findByTeacherId(teacherId);
//        }
//
//        return appointments
//                .stream()
//                .map(appointmentMapper::toResponseDto)
//                .toList();
//    }
//
//    public Appointment patchStatus(Integer id, AppointmentStatus newStatus) {
//        Appointment appointment = findById(id);
//        appointment.setStatus(newStatus);
//        return appointmentRepository.save(appointment);
//    }
//
//    public void delete(Integer id) {
//        if (!appointmentRepository.existsById(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found.");
//        }
//        appointmentRepository.deleteById(id);
//    }
//}
