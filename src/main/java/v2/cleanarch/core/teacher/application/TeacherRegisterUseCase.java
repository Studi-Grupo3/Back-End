package v2.cleanarch.core.teacher.application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.exception.EmailAlreadyExistsException;
import sptech.school.domain.exception.UserNullException;
import v2.cleanarch.core.teacher.dtos.in.TeacherRegisterDTO;
import v2.cleanarch.core.teacher.dtos.out.TeacherResponseDTO;

@Service
public class TeacherRegisterUseCase {
    private final TeacherGateway teacherGateway;

    public TeacherRegisterUseCase(TeacherGateway teacherGateway) {
        this.teacherGateway = teacherGateway;
    }

//    public T create(@Valid T entity) {
//        if (entity == null) throw new UserNullException("The user cannot be null.");
//        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
//        if (studentRepository.existsByEmail(entity.getEmail()) || teacherRepository.existsByEmail(entity.getEmail())) {
//            throw new EmailAlreadyExistsException("Email already registered for another user.");
//        }
//
//        return repository.save(entity);
//    }

    public TeacherResponseDTO create(TeacherRegisterDTO dto) {
        // Business logic can be added here (e.g., validations, transformations)

        if (dto == null) throw new UserNullException("The user cannot be null.");

        Teacher teacherSaved = TeacherMapper.INSTANCE.toEntity(dto);

        return teacherGateway.save(teacher);
    }

    public Boolean teacherExistsByEmail(String cpf) {
        return teacherGateway.existsByCpf(cpf);
    }
}
