package sptech.school.v2.cleanarch.infra.persistence.repository.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.v2.cleanarch.domain.entities.TeacherAvailability;

import java.time.DayOfWeek;
import java.util.List;

public interface TeacherAvailabilityJpaRepository extends JpaRepository<TeacherAvailability, Integer> {

    List<TeacherAvailability> findByTeacherId(Integer teacherId);

    List<TeacherAvailability> findByTeacherIdAndDayOfWeek(Integer teacherId, DayOfWeek dayOfWeek);

    @Modifying
    @Query("DELETE FROM TeacherAvailability ta WHERE ta.teacher.id = :teacherId")
    void deleteByTeacherId(@Param("teacherId") Integer teacherId);
}
