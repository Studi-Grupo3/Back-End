package sptech.school.adapters.in.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.facades.content.ContentFacade;
import sptech.school.v2.cleanarch.core.application.facades.student.StudentFacade;
import sptech.school.v2.cleanarch.core.application.mappers.ContentMapper;
import sptech.school.v2.cleanarch.core.dtos.out.ContentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Content;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class ContentController {
    @Autowired
    private ContentFacade contentFacade;
    @Autowired
    private ContentMapper mapper;
    @Autowired
    private StudentFacade studentService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE 
    )
    public ResponseEntity<@Valid ContentResponseDTO> uploadArquivo(
            @RequestPart("file") MultipartFile file, @RequestParam(value = "id") Integer idStudent) throws IOException {
        Content content = contentFacade.uploadFile(file, idStudent);
        return ResponseEntity.ok(mapper.toResponse(content));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<InputStreamResource> downloadArquivo(@PathVariable Long id) throws IOException {
//        Optional<Content> contentOpt = contentFacade.getMetadataById(id);
//        if (contentOpt.isEmpty()) return ResponseEntity.notFound().build();
//        Content content = contentOpt.get();
//
//        Optional<InputStream> streamOpt = contentFacade.findFileById(id);
//        if (streamOpt.isEmpty()) return ResponseEntity.notFound().build();
//
//        InputStreamResource resource = new InputStreamResource(streamOpt.get());
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(content.getFileSize())
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + content.getFileName() + "\"")
//                .body(resource);
//
//    }
}