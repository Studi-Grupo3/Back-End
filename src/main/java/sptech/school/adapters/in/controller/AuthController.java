package sptech.school.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.application.mappers.StudentMapper;
import v2.cleanarch.core.teacher.application.TeacherMapper;
import sptech.school.application.service.JwtService;
import sptech.school.application.service.PasswordResetService;
import sptech.school.application.service.StudentService;
import sptech.school.application.service.TeacherService;
import sptech.school.domain.dto.request.ForgotPasswordRequest;
import sptech.school.domain.dto.request.LoginRequestDTO;
import sptech.school.domain.dto.request.VerifyCodeRequest;
import sptech.school.domain.dto.response.AuthResponseDTO;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.enumerated.Role;
import sptech.school.domain.exception.AuthenticationException;
import sptech.school.domain.exception.UserNullException;

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
    private PasswordResetService service;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Student student = null;
        Teacher teacher = null;
        try {
            student = studentService.login(dto.email(), dto.password());
            System.out.println(student.getEmail() + student.getName());
        } catch (AuthenticationException ignored) {}

        try {
            teacher = teacherService.login(dto.email(), dto.password());
        } catch (AuthenticationException ignored) {}
        String token;
        System.out.println("Student: " + student);
        System.out.println("Teacher: " + teacher);
        if (student != null) {
            token = jwtService.generateToken(student.getEmail(), student.getName(), "STUDENT");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(student.getId(), student.getName(), student.getCpf(), student.getEmail(), token, Role.STUDENT);
            return ResponseEntity.ok(authResponseDTO);
        } else if (teacher != null) {
            token = jwtService.generateToken(teacher.getEmail(), teacher.getName(), "TEACHER");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(teacher.getId(), teacher.getName(), teacher.getCpf(), teacher.getEmail(), token, Role.TEACHER);
            return ResponseEntity.ok(authResponseDTO);
        }
        throw new AuthenticationException("Invalid credentials");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        boolean sent = false;

        try {
            studentService.sendResetCode(request.getEmail());
            sent = true;
        } catch (UserNullException ignored) {}

        try {
            teacherService.sendResetCode(request.getEmail());
            sent = true;
        } catch (UserNullException ignored) {}

        if (!sent) {
            throw new UserNullException("E-mail inválido");
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest body) {
        boolean valid = service.verifyCode(body.getEmail(), body.getCode());
        if (valid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de verificação inválido.");
        }
    }
}