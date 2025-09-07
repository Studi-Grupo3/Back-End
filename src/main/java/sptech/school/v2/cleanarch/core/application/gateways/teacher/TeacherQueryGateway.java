package sptech.school.v2.cleanarch.core.application.gateways.teacher;

import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.List;
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

    List<Teacher> listAll();

    Optional<Teacher> findByEmail(String email);
}
