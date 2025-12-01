package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.projections.HoursByTeacher;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.projections.TeacherBasicProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TeacherDashJpaRepository extends JpaRepository<Teacher, Integer> {

    @Query("SELECT t.id AS id, t.name AS name, t.hourlyRate AS hourlyRate, " +
            "GROUP_CONCAT(s) AS subjects " +
            "FROM Teacher t JOIN t.subjects s " +
            "GROUP BY t.id, t.name, t.hourlyRate")
    List<TeacherBasicProjection> findAllBasic();

    @Query("SELECT a.teacher.id AS teacherId, COALESCE(SUM(a.lessonDuration), 0) AS hours FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end GROUP BY a.teacher.id")
    List<HoursByTeacher> sumHoursPerTeacherBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
