package sptech.school.v2.cleanarch.infra.persistence.adapter.content;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.application.usecase.StorageServiceUseCase;
import sptech.school.v2.cleanarch.core.application.gateways.storage.FileStorageGateway;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class FileStorageAdapter implements FileStorageGateway {
    private final StorageServiceUseCase storageService;

    public FileStorageAdapter(@Qualifier("azureStorageService") StorageServiceUseCase storageService) {
        this.storageService = storageService;
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        return storageService.saveFile(file);
    }

    @Override
    public Optional<InputStream> findFile(String fileLocation) throws IOException {
        return storageService.findFile(fileLocation);
    }
}
