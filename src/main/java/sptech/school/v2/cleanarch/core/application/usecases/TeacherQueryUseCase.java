package sptech.school.v2.cleanarch.core.application.usecases;

import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Teacher;
import sptech.school.v2.cleanarch.core.application.gateways.TeacherQueryGateway;

import java.util.List;

@Service
public class TeacherQueryUseCase {
    private final TeacherQueryGateway teacherQueryGateway;

    public TeacherQueryUseCase(TeacherQueryGateway teacherQueryGateway) {
        this.teacherQueryGateway = teacherQueryGateway;
    }

    public Teacher findById(Integer id) {
        return teacherQueryGateway.findById(id);
    }

    public Boolean teacherExistsByCpf(String cpf) {
        return teacherQueryGateway.teacherExistsByCpf(cpf);
    }

    public Boolean teacherExistsByEmail(String email) {
        return teacherQueryGateway.teacherExistsByEmail(email);
    }

    public List<Teacher> listAll() {
        return teacherQueryGateway.listAll();
    }
}