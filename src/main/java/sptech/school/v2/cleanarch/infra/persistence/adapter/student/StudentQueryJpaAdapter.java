package sptech.school.v2.cleanarch.infra.persistence.adapter.student;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.StudentJpaRepository;

import java.util.List;
import java.util.Optional;

@Component
public class StudentQueryJpaAdapter implements StudentQueryGateway {
    private final StudentJpaRepository repository;

    public StudentQueryJpaAdapter(StudentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean studentExistsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean studentExistsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public Student findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Student> listAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
