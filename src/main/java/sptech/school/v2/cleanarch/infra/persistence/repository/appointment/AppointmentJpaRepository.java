package sptech.school.v2.cleanarch.infra.persistence.repository.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<Appointment, Integer> {

    @Query("select case when count(a) > 0 then true else false end from Appointment a " +
            "where a.student.id = :studentId and a.teacher.id = :teacherId and a.dateTime = :dateTime")
    boolean existsByStudentIdAndTeacherIdAndDateTime(@Param("studentId") Integer studentId,
                                                     @Param("teacherId") Integer teacherId,
                                                     @Param("dateTime") LocalDateTime dateTime);

    @Query("select case when count(a) > 0 then true else false end from Appointment a " +
            "where a.student.id = :studentId and a.teacher.id = :teacherId and a.dateTime = :dateTime and a.id <> :exceptId")
    boolean existsByStudentIdAndTeacherIdAndDateTimeAndIdNot(@Param("studentId") Integer studentId,
                                                             @Param("teacherId") Integer teacherId,
                                                             @Param("dateTime") LocalDateTime dateTime,
                                                             @Param("exceptId") Integer exceptId);

    @Query("select a from Appointment a where a.teacher.id = :teacherId")
    List<Appointment> findByTeacherId(@Param("teacherId") Integer teacherId);

    @Query("select a from Appointment a where a.teacher.id = :teacherId and a.status = :status")
    List<Appointment> findByTeacherIdAndStatus(@Param("teacherId") Integer teacherId, @Param("status") AppointmentStatus status);

    @Query("select a from Appointment a where a.student.id = :studentId")
    List<Appointment> findByStudentId(@Param("studentId") Integer studentId);

    @Query("select a from Appointment a where a.teacher.id = :teacherId and MONTH(a.dateTime) = :month and YEAR(a.dateTime) = :year")
    List<Appointment> findByTeacherIdAndMonthAndYear(@Param("teacherId") Integer teacherId, @Param("month") int month, @Param("year") int year);
}
