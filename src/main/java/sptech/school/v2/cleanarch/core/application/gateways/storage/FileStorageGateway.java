package sptech.school.v2.cleanarch.core.application.gateways.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface FileStorageGateway {
    String saveFile(MultipartFile file) throws IOException;
    Optional<InputStream> findFile(String fileLocation) throws IOException;
}
