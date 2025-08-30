package sptech.school.v2.cleanarch.core.teacher.application;

import sptech.school.domain.entity.Teacher;
import sptech.school.v2.cleanarch.core.teacher.dtos.out.TeacherResponseDTO;

public interface TeacherGateway {
    TeacherResponseDTO save(Teacher teacher);
    Boolean existsByCpf(String cpf);
}
