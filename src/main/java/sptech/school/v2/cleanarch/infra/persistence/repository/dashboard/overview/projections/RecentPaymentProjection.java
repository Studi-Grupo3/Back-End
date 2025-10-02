package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.overview.projections;

public interface RecentPaymentProjection {
    String getTeacherName();
    String getSubjects();
    Double getHourlyRate();
    Double getLessonDuration();
    String getPaymentStatus();
}
