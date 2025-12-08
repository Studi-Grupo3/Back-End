package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.teacher.projections;

public interface TeacherBasicProjection {
    Integer getId();
    String getName();
    String getSubjects();
    Double getHourlyRate();
    Boolean getDeleted(); // indica soft delete
}
