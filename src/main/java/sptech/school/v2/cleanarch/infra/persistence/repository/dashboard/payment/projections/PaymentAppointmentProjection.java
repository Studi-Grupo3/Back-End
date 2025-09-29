package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.projections;

import java.time.LocalDateTime;

public interface PaymentAppointmentProjection {
    Integer getId();
    String getTeacherName();
    String getSubject();
    Double getHourlyRate();
    Double getLessonDuration();
    String getPaymentStatus();
    LocalDateTime getDateTime();
}
