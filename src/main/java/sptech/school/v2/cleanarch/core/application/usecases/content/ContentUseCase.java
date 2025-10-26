package sptech.school.v2.cleanarch.core.application.usecases.content;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.gateways.content.ContentGateway;
import sptech.school.v2.cleanarch.core.application.gateways.storage.FileStorageGateway;
import sptech.school.v2.cleanarch.domain.entities.Content;
import sptech.school.v2.cleanarch.domain.entities.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ContentUseCase {
    private final ContentGateway contentGateway;
    private final FileStorageGateway storageGateway;

    public ContentUseCase(ContentGateway contentGateway, FileStorageGateway storageGateway) {
        this.contentGateway = contentGateway;
        this.storageGateway = storageGateway;
    }

    public Content uploadFile(MultipartFile file, Student student) throws IOException {
        String location = storageGateway.saveFile(file);
        Content content = new Content(
                file.getOriginalFilename(),
                file.getContentType(),
                location,
                file.getSize()
        );
        content.setStudent(student);
        return contentGateway.save(content);
    }

    public Optional<Content> getMetadata(Long id) {
        return contentGateway.findById(id);
    }

    public Optional<InputStream> downloadFile(Long id) throws IOException {
        Optional<Content> contentOpt = contentGateway.findById(id);
        if (contentOpt.isEmpty()) {
            return Optional.empty();
        }
        Content content = contentOpt.get();
        return storageGateway.findFile(content.getFileLocation());
    }
}
