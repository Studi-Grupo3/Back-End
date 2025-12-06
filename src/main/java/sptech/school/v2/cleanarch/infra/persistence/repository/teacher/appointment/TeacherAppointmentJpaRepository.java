package sptech.school.v2.cleanarch.infra.persistence.repository.teacher.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TeacherAppointmentJpaRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT a FROM Appointment a WHERE a.teacher.id = :teacherId AND a.status = :status ORDER BY a.dateTime ASC")
    List<Appointment> findUpcomingByTeacher(@Param("teacherId") Integer teacherId, @Param("status") AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE a.teacher.id = :teacherId ORDER BY a.dateTime DESC")
    List<Appointment> findAllByTeacherIdOrderByDateDesc(@Param("teacherId") Integer teacherId);

    // Queries auxiliares para os Stats
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.teacher.id = :tid AND a.status = :status AND DATE(a.dateTime) = :date")
    Long countByTeacherAndStatusAndDate(@Param("tid") Integer tid, @Param("status") AppointmentStatus status, @Param("date") LocalDate date);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.teacher.id = :tid AND a.status = :status AND a.dateTime BETWEEN :start AND :end")
    Long countByTeacherAndStatusBetween(@Param("tid") Integer tid, @Param("status") AppointmentStatus status, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(a.lessonDuration), 0) FROM Appointment a WHERE a.teacher.id = :tid AND a.status = :status")
    Double sumDurationByTeacherAndStatus(@Param("tid") Integer tid, @Param("status") AppointmentStatus status);

    long countByTeacherId(Integer teacherId);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.teacher.id = :teacherId AND a.status = :status")
    long countByTeacherAndStatus(@Param("teacherId") Integer teacherId, @Param("status") AppointmentStatus status);

    @Query("SELECT COALESCE(SUM(a.lessonDuration), 0) FROM Appointment a WHERE a.teacher.id = :teacherId")
    Double sumTotalDurationByTeacher(@Param("teacherId") Integer teacherId);

    @Query("SELECT a.subject, COUNT(a) FROM Appointment a WHERE a.teacher.id = :teacherId GROUP BY a.subject")
    List<Object[]> countByTeacherGroupBySubject(@Param("teacherId") Integer teacherId);

    @Query("SELECT FUNCTION('DAYOFWEEK', a.dateTime) as weekday, COUNT(a) as total FROM Appointment a WHERE a.teacher.id = :teacherId GROUP BY FUNCTION('DAYOFWEEK', a.dateTime)")
    List<Object[]> countByTeacherGroupByWeekday(@Param("teacherId") Integer teacherId);
}