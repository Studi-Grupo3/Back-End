package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.payment.projections;

import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

import java.time.LocalDateTime;

public interface PaymentAppointmentProjection {
    Integer getId();
    String getTeacherName();
    Double getHourlyRate();
    String getSubjects();
    Double getLessonDuration();
    PaymentStatus getPaymentStatus();
    LocalDateTime getDateTime();
    Double getTotalRevenue();
}
