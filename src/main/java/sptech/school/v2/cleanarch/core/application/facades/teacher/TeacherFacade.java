package sptech.school.v2.cleanarch.core.application.facades.teacher;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.mappers.ResourceFileMapper;
import sptech.school.v2.cleanarch.core.application.usecases.command.teacher.TeacherCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.content.StorageServiceUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.teacher.TeacherQueryUseCase;
import sptech.school.v2.cleanarch.core.application.utils.VerifyEmailAndCpfUtil;
import sptech.school.v2.cleanarch.core.dtos.out.ResourceFileResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.exception.UserDontHaveProfilePhoto;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.infra.persistence.repository.JpaResourceFileRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;

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
    private final TeacherJpaRepository teacherJpaRepository;

    public TeacherFacade(TeacherCommandUseCase teacherCommandUseCase,
                         TeacherQueryUseCase teacherQueryUseCase,
                         VerifyEmailAndCpfUtil verifyEmailAndCpfUtil,
                         @Qualifier("s3StorageService") StorageServiceUseCase storageServiceUseCase,
                         JpaResourceFileRepository resourceFileRepository,
                         ResourceFileMapper resourceFileMapper, TeacherJpaRepository teacherJpaRepository) {
        this.teacherCommandUseCase = teacherCommandUseCase;
        this.teacherQueryUseCase = teacherQueryUseCase;
        this.verifyEmailAndCpfUtil = verifyEmailAndCpfUtil;
        this.storageServiceUseCase = storageServiceUseCase;
        this.resourceFileRepository = resourceFileRepository;
        this.resourceFileMapper = resourceFileMapper;
        this.teacherJpaRepository = teacherJpaRepository;
    }

    @Override
//    @CacheEvict(cacheNames = "teacher", allEntries = true)
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
//    @CacheEvict(cacheNames = "teacher", allEntries = true)
    public Teacher update(Teacher teacher, Integer id) {
        verifyEmailAndCpfUtil.verify(teacher, id);
        teacher.setId(id);
        Teacher updated = teacherCommandUseCase.update(teacher);
        loadProfileImage(updated);
        return updated;
    }

    @Override
//    @CacheEvict(cacheNames = "teacher", allEntries = true)
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
//    @CacheEvict(cacheNames = "teacher", allEntries = true)
    @Transactional
    public ResourceFileResponseDTO uploadProfileImage(MultipartFile file, Integer id) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be null or empty");
        }

        // GARANTIR que obtemos a entidade Teacher gerenciada no mesmo EM
        Teacher teacher = teacherJpaRepository.findById(id)
                .orElseThrow(() -> new UserNullException("Teacher dont exist"));

        // Remove antiga imagem (S3 + DB) se existir
        ResourceFile oldProfileImage = teacher.getProfileImage();
        if (oldProfileImage != null) {
            if (oldProfileImage.getFileLocation() != null && !oldProfileImage.getFileLocation().isBlank()) {
                try {
                    storageServiceUseCase.deleteFile(oldProfileImage.getFileLocation());
                } catch (Exception ex) {
                    // não interromper o fluxo por falha na remoção do storage
                    System.out.println("Could not delete previous file from storage: " + ex.getMessage());
                }
            }
            if (oldProfileImage.getId() != null) {
                try {
                    resourceFileRepository.deleteById(oldProfileImage.getId());
                    resourceFileRepository.flush();
                } catch (Exception ex) {
                    System.out.println("Could not delete previous ResourceFile from database: " + ex.getMessage());
                }
            }
        }

        // Envia arquivo para S3 (ou storage configurado)
        String location = storageServiceUseCase.saveFile(file);

        // Cria entidade ResourceFile
        ResourceFile resourceFile = new ResourceFile(
                resolveFileName(file),
                resolveContentType(file),
                location,
                file.getSize()
        );

        // Salva e força flush para garantir que fique persistido/gerenciado
        ResourceFile savedFile = resourceFileRepository.saveAndFlush(resourceFile);

        // Re-obter explicitamente a entidade gerenciada pelo mesmo EntityManager (defensivo)
        // (às vezes saveAndFlush já retorna uma instância gerenciada, mas re-find garante que estamos no mesmo contexto)
        ResourceFile managedFile = resourceFileRepository.findById(savedFile.getId())
                .orElse(savedFile);

        // Associa ao teacher gerenciado e salva o teacher no mesmo EM/contexto
        teacher.setProfileImage(managedFile);
        Teacher updated = teacherJpaRepository.saveAndFlush(teacher);

        // Carrega/normaliza a imagem no objeto retornado (se sua aplicação faz isso)
        loadProfileImage(updated);

        return resourceFileMapper.toResponse(managedFile);
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

