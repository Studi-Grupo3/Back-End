package sptech.school.adapters.out.storage;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.application.usecase.StorageServiceUseCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service("azureStorageService")
public class AzureStorageService implements StorageServiceUseCase {

    private final BlobContainerClient containerClient;

    public AzureStorageService(
            @Value("${azure.storage.connection-string}") String connectionString,
            @Value("${azure.storage.container-name}") String containerName
    ) {
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

        this.containerClient = serviceClient.getBlobContainerClient(containerName);
        if (!this.containerClient.exists()) {
            this.containerClient.create();
        }
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        // Gera nome único para evitar sobrescrita
        String blobName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        BlockBlobClient blobClient = containerClient
                .getBlobClient(blobName)
                .getBlockBlobClient();

        try (InputStream in = file.getInputStream()) {
            blobClient.upload(in, file.getSize(), true);
        }

        return blobClient.getBlobUrl();
    }

    @Override
    public Optional<InputStream> findFile(String fileLocation) throws IOException {
        // Se você armazenar apenas o nome do blob, passe aqui somente o nome.
        // Se armazenar a URL, você pode extrair o nome a partir dela.
        String blobName = extractBlobName(fileLocation);

        BlockBlobClient blobClient = containerClient
                .getBlobClient(blobName)
                .getBlockBlobClient();

        if (!blobClient.exists()) {
            return Optional.empty();
        }

        InputStream stream = blobClient.openInputStream();
        return Optional.of(stream);
    }

    private String extractBlobName(String fileLocation) {
        if (fileLocation.startsWith("https://")) {
            return fileLocation.substring(fileLocation.lastIndexOf('/') + 1);
        }
        return fileLocation;
    }
}
