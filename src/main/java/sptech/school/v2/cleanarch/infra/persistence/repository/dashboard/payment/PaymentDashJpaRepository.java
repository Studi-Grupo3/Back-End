package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.projections.PaymentAppointmentProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentDashJpaRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT COALESCE(SUM(COALESCE(a.lessonDuration,0) / 60 * COALESCE(a.teacher.hourlyRate,0)), 0) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    Double sumTotalBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT a.teacher.id) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    long countDistinctTeachersBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(COALESCE(a.lessonDuration,0) / 60 * COALESCE(a.teacher.hourlyRate,0)), 0) FROM Appointment a WHERE a.paymentStatus = :status AND a.dateTime BETWEEN :start AND :end")
    Double sumByPaymentStatusBetween(@Param("status") PaymentStatus status, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COALESCE(SUM(COALESCE(a.lessonDuration,0) / 60 * COALESCE(a.teacher.hourlyRate,0)), 0) FROM Appointment a WHERE a.paymentStatus IN :statuses AND a.dateTime BETWEEN :start AND :end")
    Double sumByPaymentStatusesBetween(@Param("statuses") List<PaymentStatus> statuses, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
    SELECT a.teacher.id AS id,
           a.teacher.name AS teacherName,
           a.teacher.hourlyRate AS hourlyRate,
           a.paymentStatus AS paymentStatus,
           SUM(a.totalValue) AS totalRevenue,
           SUM(a.lessonDuration) AS lessonDuration,
           GROUP_CONCAT(DISTINCT a.subject) AS subjects,
           MAX(a.dateTime) AS dateTime
      FROM Appointment a
     WHERE a.dateTime BETWEEN :start AND :end
     GROUP BY a.teacher.id, a.teacher.name, a.teacher.hourlyRate, a.paymentStatus
     ORDER BY SUM(a.totalValue) DESC
""")
    List<PaymentAppointmentProjection> findAppointmentsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(DISTINCT a.teacher.id) FROM Appointment a WHERE a.paymentStatus = :status AND a.dateTime BETWEEN :start AND :end")
    long countDistinctTeachersByPaymentStatusBetween(@Param("status") PaymentStatus status, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
