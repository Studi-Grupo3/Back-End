package sptech.school.adapters.out.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.application.usecase.StorageServiceUseCase;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
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
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service("s3StorageService")
public class S3StorageService implements StorageServiceUseCase {

    private final S3Client s3Client;
    private final String bucketName;
    private final Region region;

    public S3StorageService(
            @Value("${aws.s3.access-key-id}") String accessKeyId,
            @Value("${aws.s3.secret-access-key}") String secretAccessKey,
            @Value("${aws.s3.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucketName
    ) {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.region = Region.of(region);
        S3ClientBuilder builder = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(this.region);

        this.s3Client = builder.build();
        this.bucketName = bucketName;

        ensureBucketExists();
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
            if (e.statusCode() == 404) {
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
                S3Waiter waiter = s3Client.waiter();
                waiter.waitUntilBucketExists(headBucketRequest);
            } else {
                throw e;
            }
        }
    }
}