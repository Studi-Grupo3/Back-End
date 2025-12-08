package sptech.school.v2.cleanarch.infra.persistence.command;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherCommandGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;

import java.util.Optional;

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
        Optional<Teacher> opt = repository.findById(id);
        if (opt.isEmpty()) return;
        Teacher teacher = opt.get();
        teacher.setDeleted(true);
        repository.save(teacher);
    }
}