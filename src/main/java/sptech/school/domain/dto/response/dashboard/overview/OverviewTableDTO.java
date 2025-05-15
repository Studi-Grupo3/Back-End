package sptech.school.domain.dto.response.dashboard.overview;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OverviewTableDTO {
    private String teacher;
    private LocalDateTime date;
    private double value;
    private double hours;
    private String status;

    public OverviewTableDTO() {}

    public OverviewTableDTO(String teacher, LocalDateTime date, double value, double hours, String status) {
        this.teacher = teacher;
        this.date = date;
        this.value = value;
        this.hours = hours;
        this.status = status;
    }

    public String getProfessor() {
        return teacher;
    }

    public void setProfessor(String teacher) {
        this.teacher = teacher;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
