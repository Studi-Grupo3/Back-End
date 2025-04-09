package sptech.school.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.application.mappers.StudentMapper;
import sptech.school.application.mappers.TeacherMapper;
import sptech.school.application.service.JwtService;
import sptech.school.application.service.StudentService;
import sptech.school.application.service.TeacherService;
import sptech.school.domain.dto.request.LoginRequestDTO;
import sptech.school.domain.dto.response.AuthResponseDTO;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.exception.AuthenticationException;

@RestController
@RequestMapping("/auths")
public class AuthController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Student student = null;
        Teacher teacher = null;
        try {
            student = studentService.login(dto.email(), dto.password());
        } catch (AuthenticationException ignored) {}

        try {
            teacher = teacherService.login(dto.email(), dto.password());
        } catch (AuthenticationException ignored) {}
        String token;
        System.out.println("Student: " + student);
        System.out.println("Teacher: " + teacher);
        if (student != null) {
            token = jwtService.generateToken(student.getEmail(), student.getName(), "STUDENT");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(student.getName(), student.getCpf(), student.getEmail(), token);
            return ResponseEntity.ok(authResponseDTO);
        } else if (teacher != null) {
            token = jwtService.generateToken(teacher.getEmail(), teacher.getName(), "TEACHER");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(teacher.getName(), teacher.getCpf(), teacher.getEmail(), token);
            return ResponseEntity.ok(authResponseDTO);
        }
        throw new AuthenticationException("Invalid credentials");
    }
}