package sptech.school.v2.cleanarch.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.Student;

public interface StudentJpaRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
