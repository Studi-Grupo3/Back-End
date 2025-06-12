package sptech.school.application.usecase;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface StorageServiceUseCase {
    String saveFile(MultipartFile file) throws IOException;
    Optional<InputStream> findFile(String fileLocation) throws IOException;
    void deleteFile(String fileLocation) throws IOException;
}
