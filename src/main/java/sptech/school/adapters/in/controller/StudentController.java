//package sptech.school.adapters.in.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import sptech.school.v2.cleanarch.core.application.mappers.StudentMapper;
//import sptech.school.application.service.JwtService;
//import sptech.school.application.service.StudentService;
//import sptech.school.domain.dto.request.ResetPasswordRequestDTO;
//import sptech.school.v2.cleanarch.core.dtos.in.StudentRegisterDTO;
//import sptech.school.v2.cleanarch.core.dtos.in.StudentUpdateDTO;
//import sptech.school.domain.dto.response.ResourceFileResponseDTO;
//import sptech.school.v2.cleanarch.core.dtos.out.StudentResponseDTO;
//import sptech.school.domain.entity.ResourceFile;
//import sptech.school.domain.entity.Student;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/students")
//public class StudentController {
//    @Autowired
//    private StudentService studentService;
//    @Autowired
//    private StudentMapper studentMapper;
//    @Autowired
//    private JwtService jwtService;
//
//    @PostMapping
//    public ResponseEntity<StudentResponseDTO> create(@RequestBody @Valid StudentRegisterDTO dto) {
//        Student studentCreated = studentService.create(studentMapper.dtoRequestToEntity(dto));
//
//        return ResponseEntity.status(201).body(studentMapper.toDtoResponse(studentCreated));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Integer id) {
//        StudentResponseDTO dto = studentMapper.toDtoResponse(studentService.findById(id));
//
//        return ResponseEntity.status(200).body(dto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Integer id, @RequestBody @Valid StudentUpdateDTO dto) {
//        StudentResponseDTO updated = studentMapper.toDtoResponse(studentService.update(dto, id));
//
//        return ResponseEntity.status(200).body(updated);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
//        studentService.delete(id);
//        return ResponseEntity.status(204).build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<StudentResponseDTO>> findAll() {
//        List<StudentResponseDTO> dtos = studentService.listAll();
//
//        return ResponseEntity.status(200).body(dtos);
//    }
//
//    @PostMapping(value = "/upload-profile-photo",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<@Valid ResourceFileResponseDTO> uploadImage(
//            @RequestPart("file") MultipartFile file, @RequestParam(value = "id") Integer idStudent) throws IOException {
//        ResourceFileResponseDTO dto = studentService.uploadProfileImage(file, idStudent);
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("/profile-photo/{id}")
//    public ResponseEntity<InputStreamResource> getProfilePhoto(@PathVariable Integer id) throws IOException {
//        ResourceFile profileImage = studentService.getProfileImage(id);
//
//        InputStreamResource resource = new InputStreamResource(profileImage.getInputStream());
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(profileImage.getFileType()))
//                .contentLength(profileImage.getFileSize())
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + profileImage.getFileName() + "\"")
//                .body(resource);
//    }
//
//
//    @PatchMapping("/reset-password")
//    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
//        studentService.resetPassword(request.email(), request.newPassword());
//        return ResponseEntity.noContent().build();
//    }
//}