package sptech.school.v2.cleanarch.core.application.usecases.query;

import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Student;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;

import java.util.List;

@Service
public class StudentQueryUseCase {
    private final StudentQueryGateway studentQueryGateway;

    public StudentQueryUseCase(StudentQueryGateway studentQueryGateway) {
        this.studentQueryGateway = studentQueryGateway;
    }

    public Boolean studentExistsByCpf(String cpf) {
        return studentQueryGateway.studentExistsByCpf(cpf);
    }

    public Boolean studentExistsByEmail(String email) {
        return studentQueryGateway.studentExistsByEmail(email);
    }

    public Student findById(Integer id) {
        return studentQueryGateway.findById(id);
    }
    public List<Student> listAll() {
        return studentQueryGateway.listAll();
    }
}
