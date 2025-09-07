package sptech.school.v2.cleanarch.core.application.gateways;

import sptech.school.domain.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentQueryGateway {
    boolean studentExistsByEmail(String email);

    boolean studentExistsByCpf(String cpf);

    Student findById(Integer id);

    List<Student> listAll();

    Optional<Student> findByEmail(String email);
}
