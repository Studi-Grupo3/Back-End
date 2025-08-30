package sptech.school.v2.cleanarch.core.teacher.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.teacher.dtos.in.TeacherRegisterDTO;
import sptech.school.v2.cleanarch.core.teacher.dtos.out.TeacherResponseDTO;

@Service
public class TeacherRegisterUseCase {
    private final TeacherGateway teacherGateway;
    private final PasswordEncoder passwordEncoder;

    public TeacherRegisterUseCase(TeacherGateway teacherGateway, PasswordEncoder passwordEncoder) {
        this.teacherGateway = teacherGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public TeacherResponseDTO create(TeacherRegisterDTO dto) {
        // Business logic can be added here (e.g., validations, transformations)

        if (dto == null) throw new UserNullException("The user cannot be null.");

        Teacher teacherSaved = TeacherMapper.INSTANCE.toEntity(dto);
        teacherSaved.setPassword(passwordEncoder.encode(teacherSaved.getPassword()));

        return teacherGateway.save(teacherSaved);
    }

    public Boolean teacherExistsByEmail(String cpf) {
        return teacherGateway.existsByCpf(cpf);
    }
}
