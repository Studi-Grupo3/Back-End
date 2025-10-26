package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Student;

import java.util.Optional;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Student> findByEmail(String email);
}
