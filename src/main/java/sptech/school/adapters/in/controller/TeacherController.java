package sptech.school.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.multipart.MultipartFile;
import sptech.school.application.mappers.TeacherMapper;
import sptech.school.application.service.JwtService;
import sptech.school.application.service.TeacherService;
import sptech.school.domain.dto.response.ResourceFileResponseDTO;
import sptech.school.domain.dto.request.TeacherRequestDTO;
import sptech.school.domain.dto.request.TeacherRequestUpdateDTO;
import sptech.school.domain.dto.response.TeacherResponseDTO;
import sptech.school.domain.entity.Teacher;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<TeacherResponseDTO> createUser(@RequestBody @Valid TeacherRequestDTO dto) {
        Teacher teacherCreated = teacherService.create(teacherMapper.dtoRequestToEntity(dto));

        return ResponseEntity.status(201).body(teacherMapper.toDtoResponse(teacherCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Integer id) {
        TeacherResponseDTO dto = teacherMapper.toDtoResponse(teacherService.findById(id));
        return ResponseEntity.status(200).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Integer id, @RequestBody @Valid TeacherRequestUpdateDTO dto) {
        Teacher teacherUpdated = teacherService.update(dto, id);
        return ResponseEntity.status(200).body(teacherMapper.toDtoResponse(teacherUpdated));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Integer id) {
        teacherService.delete(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> findAllTeachers() {
        List<TeacherResponseDTO> dtos = teacherService.listAll().stream()
                .map(teacherMapper::toDtoResponse).collect(Collectors.toList());
        return ResponseEntity.status(200).body(dtos);
    }

    @GetMapping()

    @PostMapping(value = "/upload-profile-photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@Valid ResourceFileResponseDTO> UploadImage(
            @RequestPart("file") MultipartFile file, @RequestParam(value = "id") Integer idTeacher) throws IOException {
        ResourceFileResponseDTO dto = teacherService.uploadProfileImage(file, idTeacher);
        return ResponseEntity.ok(dto);
    }
}