package sptech.school.v2.cleanarch.core.application.facades.teacher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.application.usecase.StorageServiceUseCase;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.usecases.teacher.TeacherCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.teacher.TeacherQueryUseCase;
import sptech.school.v2.cleanarch.core.application.utils.VerifyEmailAndCpfUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class TeacherFacade implements TeacherFacadeContract {
    private final TeacherCommandUseCase teacherCommandUseCase;
    private final TeacherQueryUseCase teacherQueryUseCase;
    private final VerifyEmailAndCpfUtil verifyEmailAndCpfUtil;
    private final StorageServiceUseCase storageServiceUseCase;

    public TeacherFacade(TeacherCommandUseCase teacherCommandUseCase, TeacherQueryUseCase teacherQueryUseCase, VerifyEmailAndCpfUtil verifyEmailAndCpfUtil, @Qualifier("azureStorageService") StorageServiceUseCase storageServiceUseCase) {
        this.teacherCommandUseCase = teacherCommandUseCase;
        this.teacherQueryUseCase = teacherQueryUseCase;
        this.verifyEmailAndCpfUtil = verifyEmailAndCpfUtil;
        this.storageServiceUseCase = storageServiceUseCase;
    }

    @Override
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
    public Teacher update(Teacher teacher, Integer id) {
        verifyEmailAndCpfUtil.verify(teacher);
        teacher.setId(id);
        Teacher updated = teacherCommandUseCase.update(teacher);
        loadProfileImage(updated);
        return updated;
    }

    @Override
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
}