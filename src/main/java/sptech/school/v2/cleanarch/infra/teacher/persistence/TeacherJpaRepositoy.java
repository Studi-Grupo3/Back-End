package sptech.school.v2.cleanarch.infra.teacher.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.domain.entity.Teacher;

@Repository
public interface TeacherJpaRepositoy extends JpaRepository<Teacher, Long> {
    boolean existsByCpf(String cpf);
}
