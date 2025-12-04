package sptech.school.v2.cleanarch.core.application.facades.teacher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.infra.persistence.repository.JpaResourceFileRepository;
import sptech.school.v2.cleanarch.core.application.usecases.content.StorageServiceUseCase;
import sptech.school.v2.cleanarch.core.dtos.out.ResourceFileResponseDTO;
import sptech.school.v2.cleanarch.core.application.mappers.ResourceFileMapper;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.exception.UserDontHaveProfilePhoto;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.usecases.command.teacher.TeacherCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.teacher.TeacherQueryUseCase;
import sptech.school.v2.cleanarch.core.application.utils.VerifyEmailAndCpfUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherFacade implements TeacherFacadeContract {
    private final TeacherCommandUseCase teacherCommandUseCase;
    private final TeacherQueryUseCase teacherQueryUseCase;
    private final VerifyEmailAndCpfUtil verifyEmailAndCpfUtil;
    private final StorageServiceUseCase storageServiceUseCase;
    private final JpaResourceFileRepository resourceFileRepository;
    private final ResourceFileMapper resourceFileMapper;

    public TeacherFacade(TeacherCommandUseCase teacherCommandUseCase,
                         TeacherQueryUseCase teacherQueryUseCase,
                         VerifyEmailAndCpfUtil verifyEmailAndCpfUtil,
                         @Qualifier("s3StorageService") StorageServiceUseCase storageServiceUseCase,
                         JpaResourceFileRepository resourceFileRepository,
                         ResourceFileMapper resourceFileMapper) {
        this.teacherCommandUseCase = teacherCommandUseCase;
        this.teacherQueryUseCase = teacherQueryUseCase;
        this.verifyEmailAndCpfUtil = verifyEmailAndCpfUtil;
        this.storageServiceUseCase = storageServiceUseCase;
        this.resourceFileRepository = resourceFileRepository;
        this.resourceFileMapper = resourceFileMapper;
    }

    @Override
    @CacheEvict(cacheNames = "teacher", allEntries = true)
    public Teacher create(Teacher teacher) {
        verifyEmailAndCpfUtil.verify(teacher);
        Teacher created = teacherCommandUseCase.create(teacher);
        loadProfileImage(created);
        return created;
    }

    @Override
    public Boolean teacherExistsByEmail(String email) {
        return teacherQueryUseCase.teacherExistsByEmail(email);
    }

    @Override
    public Teacher findById(Integer id) {
        Teacher found = teacherQueryUseCase.findById(id);
        loadProfileImage(found);
        return found;
    }

    @Override
    @CacheEvict(cacheNames = "teacher", allEntries = true)
    public Teacher update(Teacher teacher, Integer id) {
        verifyEmailAndCpfUtil.verify(teacher, id);
        teacher.setId(id);
        Teacher updated = teacherCommandUseCase.update(teacher);
        loadProfileImage(updated);
        return updated;
    }

    @Override
    @CacheEvict(cacheNames = "teacher", allEntries = true)
    public void delete(Integer id) {
        if (findById(id) == null) {
            throw new UserNullException("Teacher dont exist");
        }
        teacherCommandUseCase.delete(id);
    }

    @Override
    public Boolean teacherExistsByCpf(String cpf) {
        return teacherQueryUseCase.teacherExistsByCpf(cpf);
    }

    @Override
    public Page<Teacher> listAll(Pageable pageable) {
        Page<Teacher> teachers = teacherQueryUseCase.listAll(pageable);
        teachers.forEach(this::loadProfileImage);
        return teachers;
    }

    @Override
    public Teacher login(String email, String password) {
        Teacher teacher = teacherCommandUseCase.login(email, password);
        loadProfileImage(teacher);
        return teacher;
    }

    @Override
    @CacheEvict(cacheNames = "teacher", allEntries = true)
    public ResourceFileResponseDTO uploadProfileImage(MultipartFile file, Integer id) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        Teacher teacher = teacherQueryUseCase.findById(id);
        if (teacher == null) {
            throw new UserNullException("Teacher dont exist");
        }

        ResourceFile oldProfileImage = teacher.getProfileImage();
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
        teacher.setProfileImage(savedFile);
        Teacher updated = teacherCommandUseCase.update(teacher);
        loadProfileImage(updated);
        return resourceFileMapper.toResponse(savedFile);
    }

    @Override
    public ResourceFile getProfileImage(Integer id) throws IOException {
        Teacher teacher = teacherQueryUseCase.findById(id);
        if (teacher == null) {
            throw new UserNullException("Teacher dont exist");
        }

        ResourceFile profileImage = teacher.getProfileImage();
        if (profileImage == null) {
            throw new UserDontHaveProfilePhoto("Profile image not found for teacher.");
        }

        loadProfileImage(teacher);
        if (profileImage.getInputStream() == null) {
            throw new IOException("Failed to load profile image data from storage.");
        }

        return profileImage;
    }

    private void loadProfileImage(Teacher teacher) {
        if (teacher == null) {
            return;
        }

        ResourceFile profileImage = teacher.getProfileImage();
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
        } catch (IOException exception) {
//            LOGGER.warn("Failed to load profile image for teacher {}: {}", teacher.getId(), exception.getMessage());
        }
    }


    @Override
    public void sendResetCode(String email) {
        teacherCommandUseCase.sendResetCode(email);
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