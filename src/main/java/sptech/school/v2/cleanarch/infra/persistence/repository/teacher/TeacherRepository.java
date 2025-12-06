package sptech.school.v2.cleanarch.infra.persistence.repository.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
}

