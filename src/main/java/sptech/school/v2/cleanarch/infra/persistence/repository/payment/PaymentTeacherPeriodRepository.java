package sptech.school.v2.cleanarch.infra.persistence.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sptech.school.v2.cleanarch.domain.entities.PaymentTeacherPeriod;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

import java.util.Optional;
import java.util.List;

public interface PaymentTeacherPeriodRepository extends JpaRepository<PaymentTeacherPeriod, Integer> {
    Optional<PaymentTeacherPeriod> findByTeacherIdAndMonthAndYear(Integer teacherId, int month, int year);

    List<PaymentTeacherPeriod> findAllByMonthAndYear(int month, int year);

    @Query("SELECT p FROM PaymentTeacherPeriod p WHERE p.teacher.id = :teacherId AND p.month = :month AND p.year = :year")
    Optional<PaymentTeacherPeriod> findByTeacherPeriod(@Param("teacherId") Integer teacherId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT p FROM PaymentTeacherPeriod p WHERE p.month = :month AND p.year = :year AND p.status = :status")
    List<PaymentTeacherPeriod> findAllByMonthYearAndStatus(@Param("month") int month, @Param("year") int year, @Param("status") PaymentStatus status);
}

