package sptech.school.v2.cleanarch.core.dtos.out.dashboard.overview;

public class OverviewTableDTO {
    private String teacher;
    private String subject;
    private Double hourlyRate;
    private Double durationClass;
    private String status;

    public OverviewTableDTO() {}

    public OverviewTableDTO(String teacher, String subject, Double hourlyRate, Double durationClass, String status) {
        this.teacher = teacher;
        this.subject = subject;
        this.hourlyRate = hourlyRate;
        this.durationClass = durationClass;
        this.status = status;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getDurationClass() {
        return durationClass;
    }

    public void setDurationClass(Double durationClass) {
        this.durationClass = durationClass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
