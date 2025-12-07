package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview.projections.LessonsPerDay;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview.projections.MonthRevenue;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview.projections.RecentPaymentProjection;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OverviewDashJpaRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT COALESCE(SUM(a.totalValue), 0) FROM Appointment a WHERE a.paymentStatus = :status AND a.dateTime BETWEEN :start AND :end")
    Double sumTotalByPaymentStatusBetween(@Param("status") PaymentStatus status,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("""
        SELECT MONTH(a.dateTime) AS month,
               COALESCE(SUM(a.totalValue), 0) AS total
          FROM Appointment a
         WHERE a.paymentStatus = :status
           AND a.dateTime BETWEEN :start AND :end
         GROUP BY MONTH(a.dateTime)
         ORDER BY MONTH(a.dateTime)
    """)
    List<MonthRevenue> sumPaidByMonthBetween(@Param("status") PaymentStatus status,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query("""
        SELECT FUNCTION('DAYNAME', a.dateTime) AS dayName,
               COUNT(a) AS total
          FROM Appointment a
         WHERE a.dateTime BETWEEN :start AND :end
         GROUP BY FUNCTION('DAYNAME', a.dateTime)
         ORDER BY MIN(a.dateTime)
    """)
    List<LessonsPerDay> countLessonsByDayNameBetween(@Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.dateTime BETWEEN :start AND :end")
    long countAppointmentsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("""
        SELECT COALESCE(SUM(COALESCE(a.lessonDuration, 0) * COALESCE(a.teacher.hourlyRate, 0)), 0)
          FROM Appointment a
         WHERE a.paymentStatus = :status
           AND a.dateTime BETWEEN :start AND :end
    """)
    Double sumPendingAmountBetween(@Param("status") PaymentStatus status,
                                   @Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

    @Query("""
        SELECT a.teacher.name AS teacherName,
               FUNCTION('GROUP_CONCAT', DISTINCT s) AS subjects,
               a.teacher.hourlyRate AS hourlyRate,
               SUM(a.lessonDuration) AS lessonDuration,
               a.paymentStatus AS paymentStatus,
               SUM(a.totalValue) AS totalRevenue
          FROM Appointment a
          JOIN a.teacher t
          JOIN t.subjects s
         WHERE a.paymentStatus = :status
           AND a.dateTime BETWEEN :start AND :end
         GROUP BY t.id, a.teacher.name, a.teacher.hourlyRate, a.paymentStatus
         ORDER BY SUM(a.totalValue) DESC
    """)
    List<RecentPaymentProjection> findRecentPaymentsByStatusBetween(@Param("status") PaymentStatus status,
                                                                    @Param("start") LocalDateTime start,
                                                                    @Param("end") LocalDateTime end,
                                                                    Pageable pageable);
}
