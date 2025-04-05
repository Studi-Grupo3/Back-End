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
import sptech.school.application.service.StudentService;
import sptech.school.application.service.TeacherService;
import sptech.school.domain.dto.request.LoginRequestDTO;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    @PostMapping("/login")
    ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        try {
            var student = studentService.login(email, password);
            var teacher = teacherService.login(email, password);
            if (student != null) {
                return ResponseEntity.ok(studentMapper.toDtoResponse(student));
            } else if (teacher != null) {
                return ResponseEntity.ok(teacherMapper.toDtoResponse(teacher));
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during login: " + e.getMessage());
        }
    }

}
