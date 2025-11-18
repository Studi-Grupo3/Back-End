package sptech.school.v2.cleanarch.core.application.gateways.student;

import sptech.school.v2.cleanarch.domain.entities.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface StudentQueryGateway {
    boolean studentExistsByEmail(String email);

    boolean studentExistsByCpf(String cpf);

    Student findById(Integer id);

    Page<Student> listAll(Pageable pageable);

    Optional<Student> findByEmail(String email);

    Optional<Long> findIdByEmail(String email);

    Optional<Long> findByCpf(String cpf);
}
