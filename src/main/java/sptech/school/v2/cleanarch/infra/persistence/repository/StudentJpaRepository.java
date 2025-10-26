package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.Student;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Student> findByEmail(String email);
}
