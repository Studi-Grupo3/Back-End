package sptech.school.adapters.out.persistence.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.domain.entity.Teacher;

@Repository
public interface TeacherDashRepository extends JpaRepository<Teacher, Integer> {

}

