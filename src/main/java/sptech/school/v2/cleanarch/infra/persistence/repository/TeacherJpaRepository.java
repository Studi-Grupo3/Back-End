package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.Optional;

@Repository
public interface TeacherJpaRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Optional<Teacher> findByEmail(String email);

    Optional<Integer> findIdByEmail(String email);

    Optional<Integer> findIdByCpf(String cpf);
}
