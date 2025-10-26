package sptech.school.v2.cleanarch.core.application.gateways.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.Optional;

/**
 * Gateway de consulta (query) para operações relacionadas a professores.
 * para incluir métodos de consulta específicos, como encontrar professores
 * verificar se existem professores com um determinado CPF, por ID, email, ou outros critérios.
 */
public interface TeacherQueryGateway {
    boolean teacherExistsByEmail(String email);

    boolean teacherExistsByCpf(String cpf);

    Teacher findById(Integer id);

    Page<Teacher> listAll(Pageable pageable);

    Optional<Teacher> findByEmail(String email);
}
