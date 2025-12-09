package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview.projections;

import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;

public interface RecentPaymentProjection {
    String getTeacherName();
    String getSubjects();
    Double getHourlyRate();
    Double getLessonDuration();
    PaymentStatus getPaymentStatus();
    Double getTotalRevenue();
}
