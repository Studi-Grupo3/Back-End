package sptech.school.v2.cleanarch.core.application.facades;

import sptech.school.domain.entity.Teacher;

import java.util.List;

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
    List<Teacher> listAll();
    Teacher update(Teacher teacher, Integer id);
    void delete(Integer id);
    Boolean teacherExistsByEmail(String email);
    Boolean teacherExistsByCpf(String cpf);
    Teacher findById(Integer id);
}