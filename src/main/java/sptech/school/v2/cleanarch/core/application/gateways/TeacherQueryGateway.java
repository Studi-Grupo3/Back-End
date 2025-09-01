package sptech.school.v2.cleanarch.core.application.gateways;

import sptech.school.domain.entity.Teacher;

import java.util.List;

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
}
