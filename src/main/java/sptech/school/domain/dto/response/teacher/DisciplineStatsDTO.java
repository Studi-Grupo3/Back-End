package sptech.school.domain.dto.response.teacher;

import sptech.school.domain.enumerated.Subject;


public class DisciplineStatsDTO {

    private Subject subject;
    private Long count;

    public DisciplineStatsDTO(Subject subject, Long count) {
        this.subject = subject;
        this.count = count;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
