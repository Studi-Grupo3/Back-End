package sptech.school.v2.cleanarch.core.application.gateways;

import sptech.school.domain.entity.Teacher;

import java.util.Optional;

/**
 * Interface de gateway para operações de comando relacionadas a entidades Teacher.
 * Esta interface define métodos para salvar, update, delete.
 */
public interface TeacherCommandGateway {
    Teacher save(Teacher teacher);
    Teacher update(Teacher teacher);
    void delete(Integer id);
}