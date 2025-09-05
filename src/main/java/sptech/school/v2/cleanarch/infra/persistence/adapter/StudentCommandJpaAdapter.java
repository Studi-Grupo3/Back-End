package sptech.school.v2.cleanarch.infra.persistence.adapter;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import sptech.school.domain.entity.Student;
import sptech.school.v2.cleanarch.core.application.gateways.StudentCommandGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.StudentJpaRepository;

import java.util.Optional;

@Component
public class StudentCommandJpaAdapter implements StudentCommandGateway {
    private final StudentJpaRepository repository;

    public StudentCommandJpaAdapter(StudentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student save(Student student) {
        return repository.save(student);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return repository.save(student);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
