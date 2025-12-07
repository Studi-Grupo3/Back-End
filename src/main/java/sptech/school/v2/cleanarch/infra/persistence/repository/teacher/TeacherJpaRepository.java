package sptech.school.v2.cleanarch.infra.persistence.repository.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.Optional;

@Repository
public interface TeacherJpaRepository extends JpaRepository<Teacher, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    Optional<Teacher> findByEmail(String email);

    @Query("SELECT t.id FROM Teacher t WHERE t.email = :email")
    Optional<Integer> findIdByEmail(@Param("email") String email);

    @Query("SELECT t.id FROM Teacher t WHERE t.cpf = :cpf")
    Optional<Integer> findIdByCpf(@Param("cpf") String cpf);

    Page<Teacher> findAllByDeletedFalse(Pageable pageable);

    Optional<Teacher> findByEmailAndDeletedFalse(String email);
}
