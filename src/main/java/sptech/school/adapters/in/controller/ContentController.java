package sptech.school.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.application.mappers.ContentMapper;
import sptech.school.application.service.ContentService;
import sptech.school.application.service.StudentService;
import sptech.school.domain.dto.response.ContentResponseDTO;
import sptech.school.domain.entity.Content;
import sptech.school.domain.entity.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class ContentController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentMapper mapper;
    @Autowired
    private StudentService studentService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<@Valid ContentResponseDTO> uploadArquivo(
            @RequestPart("file") MultipartFile file, @RequestParam Integer idStudent) throws IOException {
        Student student = studentService.findById(idStudent);
        Content content = contentService.saveFile(file, student);
        return ResponseEntity.ok(mapper.toResponse(content));
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<@Valid ContentResponseDTO> getMetadata(@PathVariable Long id) {
        return contentService.getMetadataById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadArquivo(@PathVariable Long id) throws IOException {
        Optional<Content> contentOpt = contentService.getMetadataById(id);
        if (contentOpt.isEmpty()) return ResponseEntity.notFound().build();
        Content content = contentOpt.get();

        Optional<InputStream> streamOpt = contentService.findFileById(id);
        if (streamOpt.isEmpty()) return ResponseEntity.notFound().build();

        InputStreamResource resource = new InputStreamResource(streamOpt.get());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(content.getFileSize())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + content.getFileName() + "\"")
                .body(resource);

    }
}