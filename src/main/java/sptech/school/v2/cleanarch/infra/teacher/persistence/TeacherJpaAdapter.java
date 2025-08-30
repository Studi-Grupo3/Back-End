package sptech.school.v2.cleanarch.infra.teacher.persistence;

import org.springframework.stereotype.Component;
import sptech.school.domain.entity.Teacher;
import sptech.school.v2.cleanarch.core.teacher.application.TeacherGateway;
import sptech.school.v2.cleanarch.core.teacher.application.TeacherMapper;
import sptech.school.v2.cleanarch.core.teacher.dtos.out.TeacherResponseDTO;
import sptech.school.v2.cleanarch.domain.exception.CpfAlreadyExistsException;

@Component
public class TeacherJpaAdapter implements TeacherGateway {

    private final TeacherJpaRepositoy repository;

    public TeacherJpaAdapter(TeacherJpaRepositoy repository) {
        this.repository = repository;
    }

    @Override
    public TeacherResponseDTO save(Teacher teacher) {
        if (existsByCpf(teacher.getCpf())) {
            throw new CpfAlreadyExistsException("CPF already exists");
        }
        Teacher teacherSaved = repository.save(teacher);
        TeacherResponseDTO dto = TeacherMapper.INSTANCE.toResponseDTO(teacherSaved);

        return dto;
    }

    @Override
    public Boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }
}
