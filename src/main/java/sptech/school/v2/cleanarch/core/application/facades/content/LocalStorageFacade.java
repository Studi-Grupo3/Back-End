package sptech.school.v2.cleanarch.core.application.facades.content;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.usecases.content.StorageServiceUseCase;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service("localStorageService")
public class LocalStorageFacade implements StorageServiceUseCase {
    // Esse diretório é setado no application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        Path uploadFile = Paths.get(uploadDir);

        if (!Files.exists(uploadFile)) {
            Files.createDirectories(uploadFile);
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadFile.resolve(Objects.requireNonNull(fileName));
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }

    @Override
    public Optional<InputStream> findFile(String fileLocation) throws IOException {
        Path filePath = Paths.get(fileLocation);

        return Files.exists(filePath)
                ? Optional.of(Files.newInputStream(filePath))
                : Optional.empty();
    }

    @Override
    public void deleteFile(String fileLocation) throws IOException {
        Path filePath = Paths.get(fileLocation);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        } else {
            throw new IOException("Arquivo não encontrado: " + fileLocation);
        }
    }
}
