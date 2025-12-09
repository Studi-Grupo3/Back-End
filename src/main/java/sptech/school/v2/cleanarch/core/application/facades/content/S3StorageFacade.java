package sptech.school.v2.cleanarch.core.application.facades.content;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.usecases.content.StorageServiceUseCase;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("s3StorageService")
public class S3StorageFacade implements StorageServiceUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3StorageFacade.class);

    private final S3Client s3Client;
    private final String bucketName;
    private final Region region;

    public S3StorageFacade(
            @Value("${aws.s3.access-key-id}") String accessKeyId,
            @Value("${aws.s3.secret-access-key}") String secretAccessKey,
            // session token adicionado — opcional (para credenciais temporárias ASIA...)
            @Value("${aws.s3.session-token:}") String sessionToken,
            @Value("${aws.s3.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucketName
    ) {
        this.region = Region.of(region);
        S3ClientBuilder builder = S3Client.builder()
                .region(this.region);

        // Se houver sessionToken (credenciais temporárias), usa AwsSessionCredentials
        if (sessionToken != null && !sessionToken.isBlank()) {
            AwsSessionCredentials sessionCreds = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken);
            builder.credentialsProvider(StaticCredentialsProvider.create(sessionCreds));
            LOGGER.info("S3 client configured with temporary session credentials (session token present)");
        } else {
            AwsCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
            builder.credentialsProvider(StaticCredentialsProvider.create(credentials));
            LOGGER.info("S3 client configured with static credentials");
        }

        this.s3Client = builder.build();
        this.bucketName = bucketName;

        // moved bucket existence check out of constructor to avoid failing app startup on S3 errors
    }

    @PostConstruct
    private void init() {
        try {
            ensureBucketExists();
        } catch (S3Exception | SdkClientException e) {
            // Log a warning but don't stop application startup. In many dev/test environments
            // credentials or network to S3 may not be available and we want the app to keep running.
            LOGGER.warn("S3 initialization skipped due to error: {}. S3-backed features will be unavailable.", e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("Unexpected error during S3 initialization: {}", e.getMessage());
        }
    }

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "_" + Objects.requireNonNullElse(file.getOriginalFilename(), "file");

        try (InputStream in = file.getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(in, file.getSize()));
        }

        return key;
    }

    @Override
    public Optional<InputStream> findFile(String fileLocation) throws IOException {
        String key = resolveKey(fileLocation);
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
            return Optional.of(response);
        } catch (NoSuchKeyException e) {
            return Optional.empty();
        } catch (S3Exception | SdkClientException e) {
            throw new IOException("Failed to retrieve file from S3", e);
        }
    }

    @Override
    public void deleteFile(String fileLocation) throws IOException {
        String key = resolveKey(fileLocation);
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(request);
        } catch (S3Exception | SdkClientException e) {
            throw new IOException("Failed to delete file from S3", e);
        }
    }

    private String resolveKey(String fileLocation) {
        if (fileLocation == null) {
            throw new IllegalArgumentException("File location must not be null");
        }

        if (fileLocation.startsWith("s3://")) {
            int index = fileLocation.indexOf('/', 5);
            return index > 0 ? fileLocation.substring(index + 1) : fileLocation;
        }

        if (fileLocation.startsWith("http://") || fileLocation.startsWith("https://")) {
            int index = fileLocation.lastIndexOf('/');
            return index >= 0 ? fileLocation.substring(index + 1) : fileLocation;
        }

        return fileLocation;
    }

    private void ensureBucketExists() {
        HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();

        try {
            s3Client.headBucket(headBucketRequest);
        } catch (S3Exception e) {
            // If bucket not found (404) try to create it. For other status codes, log and return.
            int status = e.statusCode();
            if (status == 404) {
                CreateBucketRequest.Builder createBucketBuilder = CreateBucketRequest.builder()
                        .bucket(bucketName);

                if (!Region.US_EAST_1.equals(region)) {
                    createBucketBuilder.createBucketConfiguration(
                            CreateBucketConfiguration.builder()
                                    .locationConstraint(region.id())
                                    .build()
                    );
                }

                s3Client.createBucket(createBucketBuilder.build());
                try {
                    S3Waiter waiter = s3Client.waiter();
                    waiter.waitUntilBucketExists(headBucketRequest);
                } catch (Exception ex) {
                    LOGGER.warn("Failed to wait for bucket creation: {}", ex.getMessage());
                }
            } else {
                // Don't rethrow: a 403 (forbidden) or connectivity issue should not kill the whole app on startup
                LOGGER.warn("S3 headBucket returned status {}: {}", status, e.getMessage());
            }
        }
    }
}