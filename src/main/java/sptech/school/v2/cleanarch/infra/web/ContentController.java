package sptech.school.v2.cleanarch.infra.web;

import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.facades.content.ContentFacadeContract;
import sptech.school.v2.cleanarch.core.application.mappers.ContentMapper;
import sptech.school.v2.cleanarch.core.dtos.out.ContentResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class ContentController {

    private final ContentFacadeContract contentFacade;
    private final ContentMapper contentMapper;

    public ContentController(ContentFacadeContract contentFacade, ContentMapper contentMapper) {
        this.contentFacade = contentFacade;
        this.contentMapper = contentMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<@Valid ContentResponseDTO> uploadArquivo(
            @RequestPart("file") MultipartFile file, @RequestParam("id") Integer idStudent) throws IOException {
        Content content = contentFacade.uploadFile(file, idStudent);
        return ResponseEntity.ok(contentMapper.toResponse(content));
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<@Valid ContentResponseDTO> getMetadata(@PathVariable Long id) {
        return contentFacade.getMetadata(id)
                .map(contentMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> downloadArquivo(@PathVariable Long id) throws IOException {
        Optional<InputStream> streamOpt = contentFacade.downloadFile(id);
        if (streamOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Content> contentOpt = contentFacade.getMetadata(id);
        if (contentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Content content = contentOpt.get();
        InputStreamResource resource = new InputStreamResource(streamOpt.get());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(content.getFileSize())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + content.getFileName() + "\"")
                .body(resource);
    }
}
