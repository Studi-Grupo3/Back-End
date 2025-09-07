package sptech.school.v2.cleanarch.core.application.facades.content;

import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.domain.entities.Content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface ContentFacadeContract {
    Content uploadFile(MultipartFile file, Integer studentId) throws IOException;
    Optional<Content> getMetadata(Long id);
    Optional<InputStream> downloadFile(Long id) throws IOException;
}
