package sptech.school.v2.cleanarch.core.application.facades.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

/**
 Para adicionar novos métodos, siga os passos:
    1. Adicione o método na interface TeacherFacadeContract.
    2. Implemente o método na classe TeacherFacade.
    3. Se necessário, adicione o método na interface TeacherCommandGateway ou
        TeacherQueryGateway, dependendo se é um comando (criação, atualização, exclusão) ou uma consulta (leitura).
    4. Implemente o método na classe que implementa o gateway correspondente.
    5. Se necessário, adicione um caso de uso (UseCase) para
        encapsular a lógica de negócio relacionada ao novo método.
    6. Implemente o método na classe UseCase correspondente.
    7. Atualize os testes para cobrir o novo método, se aplicável.
 */
public interface TeacherFacadeContract {
    Teacher create(Teacher teacher);
    Page<Teacher> listAll(Pageable pageable);
    Teacher update(Teacher teacher, Integer id);
    void delete(Integer id);
    Boolean teacherExistsByEmail(String email);
    Boolean teacherExistsByCpf(String cpf);
    Teacher findById(Integer id);
    Teacher login(String email, String password);
    void sendResetCode(String email);
}