package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.domain.entity.Appointment;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.AppointmentNext5;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.StatusCount;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.appointment.projections.WeekCount;

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

    @Query(value = """
       SELECT
         (WEEK(a.date_time, 1) - WEEK(DATE_SUB(a.date_time, INTERVAL DAY(a.date_time)-1 DAY), 1) + 1) AS week,
         COUNT(*) AS total
       FROM tb_appointment a
       WHERE a.date_time BETWEEN :start AND :end
       GROUP BY week
       ORDER BY week
       """, nativeQuery = true)
    List<WeekCount> countByWeekOfMonthBetween(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);

    @Query("""
      SELECT a.student.name AS studentName,
             a.teacher.name AS teacherName,
             a.dateTime AS dateTime,
             a.lessonDuration AS duration,
             a.location AS location,
             a.status AS status
        FROM Appointment a
       WHERE a.dateTime BETWEEN :start AND :end
       ORDER BY a.dateTime ASC
    """)
    List<AppointmentNext5> findNextAppointmentsBetween(@Param("start") LocalDateTime start,
                                                       @Param("end") LocalDateTime end,
                                                       Pageable pageable);
}
