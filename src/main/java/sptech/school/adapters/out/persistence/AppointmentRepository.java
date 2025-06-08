package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.Appointment;
import sptech.school.domain.enumerated.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    boolean existsByStudentIdAndTeacherIdAndDateTime(Integer userStudentId, Integer userTeacherId, LocalDateTime dateTime);

    List<Appointment> findByDateTimeBetween(LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<Appointment> findByPaymentStatus(PaymentStatus paymentStatus);
}