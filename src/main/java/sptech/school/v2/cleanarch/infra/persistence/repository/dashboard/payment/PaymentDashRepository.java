package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.projections.PaymentAppointmentProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentDashRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT COALESCE(SUM(COALESCE(a.lessonDuration,0) * COALESCE(a.teacher.hourlyRate,0)), 0) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    Double sumTotalBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT a.teacher.id) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    long countDistinctTeachersBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(COALESCE(a.lessonDuration,0) * COALESCE(a.teacher.hourlyRate,0)), 0) FROM Appointment a WHERE a.paymentStatus = :status AND a.dateTime BETWEEN :start AND :end")
    Double sumByPaymentStatusBetween(@Param("status") PaymentStatus status, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT a.teacher.id) FROM Appointment a WHERE a.paymentStatus = :status AND a.dateTime BETWEEN :start AND :end")
    long countDistinctTeachersByPaymentStatusBetween(@Param("status") PaymentStatus status, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT a.id AS id, a.teacher.name AS teacherName, a.teacher.subjects AS subject, a.teacher.hourlyRate AS hourlyRate, a.lessonDuration AS lessonDuration, a.paymentStatus AS paymentStatus, a.dateTime AS dateTime FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end ORDER BY a.dateTime DESC")
    List<PaymentAppointmentProjection> findAppointmentsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
