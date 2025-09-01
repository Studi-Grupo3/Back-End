package sptech.school.v2.cleanarch.core.application.gateways;

import sptech.school.domain.entity.Student;

public interface StudentQueryGateway {
    boolean studentExistsByEmail(String email);

    boolean studentExistsByCpf(String cpf);

    Student findById(Integer id);

    java.util.List<Student> listAll();
}
