package sptech.school.v2.cleanarch.core.application.gateways.teacher;

import sptech.school.domain.entity.Teacher;

/**
 * Interface de gateway para operações de comando relacionadas a entidades Teacher.
 * Esta interface define métodos para salvar, update, delete.
 */
public interface TeacherCommandGateway {
    Teacher save(Teacher teacher);
    Teacher update(Teacher teacher);
    void delete(Integer id);
}