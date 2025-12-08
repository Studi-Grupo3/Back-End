package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Student;

import java.util.Optional;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Optional<Student> findByEmail(String email);

    @Query("SELECT s.id FROM Student s WHERE s.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Query("SELECT s.id FROM Student s WHERE s.cpf = :cpf")
    Optional<Integer> findIdByCpf(@Param("cpf") String cpf);
}
