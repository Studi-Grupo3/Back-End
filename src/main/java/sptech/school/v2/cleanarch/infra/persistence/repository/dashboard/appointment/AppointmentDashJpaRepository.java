package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.AppointmentNext5;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.StatusCount;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.SubjectCount;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentDashJpaRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    long countTotalBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
        SELECT a.status AS status, COUNT(a) AS total
          FROM Appointment a
         WHERE a.dateTime BETWEEN :start AND :end
         GROUP BY a.status
    """)
    List<StatusCount> countByStatusBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT a.student.id) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    long countDistinctStudentsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT AVG(a.lessonDuration) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    Double averageDurationBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
        SELECT a.dateTime
        FROM Appointment a 
        WHERE a.dateTime BETWEEN :start AND :end
        AND a.status = 'COMPLETED'
    """)
    List<LocalDateTime> findCompletedLessonDatesBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
        SELECT a.student.name AS studentName, a.teacher.name AS teacherName, a.dateTime AS dateTime, 
               a.lessonDuration AS duration, a.location AS location, CAST(a.status AS string) AS status,
               a.subject AS subject
        FROM Appointment a 
        WHERE a.dateTime BETWEEN :start AND :end
        ORDER BY a.dateTime ASC
    """)
    List<AppointmentNext5> findNextAppointmentsBetween(@Param("start") LocalDateTime start,
                                                       @Param("end") LocalDateTime end,
                                                       Pageable pageable);

    @Query("""
        SELECT a.subject AS label, COUNT(a) AS total
          FROM Appointment a
         WHERE a.dateTime BETWEEN :start AND :end
           AND a.status <> 'CANCELLED'
         GROUP BY a.subject
         ORDER BY COUNT(a) DESC
    """)
    List<SubjectCount> countBySubjectBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}