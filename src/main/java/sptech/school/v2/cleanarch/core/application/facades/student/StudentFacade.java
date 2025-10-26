package sptech.school.v2.cleanarch.core.application.facades.student;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.infra.persistence.repository.JpaResourceFileRepository;
import sptech.school.v2.cleanarch.core.application.usecases.content.StorageServiceUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.ResourceFileResponseDTO;
import sptech.school.v2.cleanarch.core.application.mappers.ResourceFileMapper;
import sptech.school.v2.cleanarch.core.application.usecases.command.student.StudentCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.student.StudentQueryUseCase;
import sptech.school.v2.cleanarch.core.application.utils.VerifyEmailAndCpfUtil;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.exception.UserDontHaveProfilePhoto;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentFacade implements StudentFacadeContract {
    private final StudentCommandUseCase studentCommandUseCase;
    private final StudentQueryUseCase studentQueryUseCase;
    private final VerifyEmailAndCpfUtil verifyEmailAndCpfUtil;
    private final StorageServiceUseCase storageServiceUseCase;
    private final JpaResourceFileRepository resourceFileRepository;
    private final ResourceFileMapper resourceFileMapper;

    public StudentFacade(StudentCommandUseCase studentCommandUseCase,
                         StudentQueryUseCase studentQueryUseCase,
                         VerifyEmailAndCpfUtil verifyEmailAndCpfUtil,
                         @Qualifier("s3StorageService") StorageServiceUseCase storageServiceUseCase,
                         JpaResourceFileRepository resourceFileRepository,
                         ResourceFileMapper resourceFileMapper) {
        this.studentCommandUseCase = studentCommandUseCase;
        this.studentQueryUseCase = studentQueryUseCase;
        this.verifyEmailAndCpfUtil = verifyEmailAndCpfUtil;
        this.storageServiceUseCase = storageServiceUseCase;
        this.resourceFileRepository = resourceFileRepository;
        this.resourceFileMapper = resourceFileMapper;
    }

    @Override
    public Student create(Student student) {
        verifyEmailAndCpfUtil.verify(student);
        Student created = studentCommandUseCase.create(student);
        loadProfileImage(created);
        return created;
    }

    @Override
    public Page<Student> listAll(Pageable pageable) {
        Page<Student> students = studentQueryUseCase.listAll(pageable);
        students.forEach(this::loadProfileImage);
        return students;
    }

    @Override
    public Student update(Student student, Integer id) {
        verifyEmailAndCpfUtil.verify(student);
        student.setId(id);
        Student updated = studentCommandUseCase.update(student);
        loadProfileImage(updated);
        return updated;
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            throw new UserNullException("Student dont exist");
        }
        studentCommandUseCase.delete(id);
    }

    @Override
    public Student findById(Integer id) {
        Student found = studentQueryUseCase.findById(id);
        if (found == null) {
            throw new UserNullException("Student dont exist");
        }
        loadProfileImage(found);
        return found;
    }

    @Override
    public Boolean studentExistsByEmail(String email) {
        return studentQueryUseCase.studentExistsByEmail(email);
    }

    @Override
    public Boolean studentExistsByCpf(String cpf) {
        return studentQueryUseCase.studentExistsByCpf(cpf);
    }


    @Override
    public Student login(String email, String password) {
        Student student = studentCommandUseCase.login(email, password);
        loadProfileImage(student);
        return student;
    }

    @Override
    public void sendResetCode(String email) {
        studentCommandUseCase.sendResetCode(email);
    }

    @Override
    public ResourceFileResponseDTO uploadProfileImage(MultipartFile file, Integer id) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        Student student = studentQueryUseCase.findById(id);
        if (student == null) {
            throw new UserNullException("Student dont exist");
        }

        ResourceFile oldProfileImage = student.getProfileImage();
        if (oldProfileImage != null) {
            if (oldProfileImage.getFileLocation() != null && !oldProfileImage.getFileLocation().isBlank()) {
                storageServiceUseCase.deleteFile(oldProfileImage.getFileLocation());
            }
            if (oldProfileImage.getId() != null) {
                resourceFileRepository.deleteById(oldProfileImage.getId());
            }
        }

        String location = storageServiceUseCase.saveFile(file);
        ResourceFile resourceFile = new ResourceFile(
                resolveFileName(file),
                resolveContentType(file),
                location,
                file.getSize()
        );

        ResourceFile savedFile = resourceFileRepository.save(resourceFile);
        student.setProfileImage(savedFile);
        Student updated = studentCommandUseCase.update(student);
        loadProfileImage(updated);
        return resourceFileMapper.toResponse(savedFile);
    }

    @Override
    public ResourceFile getProfileImage(Integer id) throws IOException {
        Student student = studentQueryUseCase.findById(id);
        if (student == null) {
            throw new UserNullException("Student dont exist");
        }

        ResourceFile profileImage = student.getProfileImage();
        if (profileImage == null) {
            throw new UserDontHaveProfilePhoto("Profile image not found for student.");
        }

        loadProfileImage(student);
        if (profileImage.getInputStream() == null) {
            throw new IOException("Failed to load profile image data from storage.");
        }
        return profileImage;
    }

    private void loadProfileImage(Student student) {
        if (student == null) {
            return;
        }

        ResourceFile profileImage = student.getProfileImage();
        if (profileImage == null) {
            return;
        }

        String fileLocation = profileImage.getFileLocation();
        if (fileLocation == null || fileLocation.isBlank()) {
            return;
        }

        try {
            Optional<InputStream> streamOpt = storageServiceUseCase.findFile(fileLocation);
            if (streamOpt.isEmpty()) {
                return;
            }

            try (InputStream stream = streamOpt.get()) {
                byte[] data = stream.readAllBytes();
                profileImage.setInputStream(new ByteArrayInputStream(data));
            }
        } catch (IOException ignored) {
            // Intentionally ignore to avoid breaking main flow when image retrieval fails
        }
    }

    private String resolveFileName(MultipartFile file) {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            return Objects.toString(java.util.UUID.randomUUID());
        }
        return originalName;
    }

    private String resolveContentType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank()) {
            return "application/octet-stream";
        }
        return contentType;
    }
}