package sptech.school.v2.cleanarch.infra.persistence;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sptech.school.domain.entity.Teacher;
import sptech.school.v2.cleanarch.core.application.gateways.TeacherCommandGateway;

@Component
public class TeacherCommandJpaAdapter implements TeacherCommandGateway {

    private final TeacherJpaRepository repository;

    public TeacherCommandJpaAdapter(TeacherJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Teacher save(Teacher teacher) {
        return repository.save(teacher);
    }

    @Override
    @Transactional
    public Teacher update(Teacher teacher) {
        return repository.save(teacher);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}