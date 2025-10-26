package sptech.school.v2.cleanarch.core.application.usecases.query.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.Optional;

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

    public Page<Teacher> listAll(Pageable pageable) {
        return teacherQueryGateway.listAll(pageable);
    }

    public Optional<Teacher> findByEmail(String email) {
        return teacherQueryGateway.findByEmail(email);
    }
}