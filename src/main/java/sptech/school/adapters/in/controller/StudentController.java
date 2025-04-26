package sptech.school.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.application.mappers.StudentMapper;
import sptech.school.application.service.JwtService;
import sptech.school.application.service.StudentService;
import sptech.school.domain.dto.request.LoginRequestDTO;
import sptech.school.domain.dto.request.ResetPasswordRequestDTO;
import sptech.school.domain.dto.request.StudentRequestDTO;
import sptech.school.domain.dto.request.StudentRequestUpdateDTO;
import sptech.school.domain.dto.response.AuthResponseDTO;
import sptech.school.domain.dto.response.ResourceFileResponseDTO;
import sptech.school.domain.dto.response.StudentResponseDTO;
import sptech.school.domain.entity.Student;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody @Valid StudentRequestDTO dto) {
        Student studentCreated = studentService.create(studentMapper.dtoRequestToEntity(dto));

        return ResponseEntity.status(201).body(studentMapper.toDtoResponse(studentCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Integer id) {
        StudentResponseDTO dto = studentMapper.toDtoResponse(studentService.findById(id));

        return ResponseEntity.status(200).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Integer id, @RequestBody @Valid StudentRequestUpdateDTO dto) {
        StudentResponseDTO updated = studentMapper.toDtoResponse(studentService.update(dto, id));

        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        studentService.delete(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> findAll() {
        List<StudentResponseDTO> dtos = studentService.listAll();

        return ResponseEntity.status(200).body(dtos);
    }

    @PostMapping("/upload-profile-photo")
    public ResponseEntity<@Valid ResourceFileResponseDTO> uploadArquivo(
            @RequestParam("file") MultipartFile file) throws IOException {
        ResourceFileResponseDTO dto = studentService.saveFile(file);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        studentService.resetPassword(request.email(), request.newPassword());
        return ResponseEntity.noContent().build();
    }
}