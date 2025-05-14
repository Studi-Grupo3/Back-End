package sptech.school.domain.dto.response.dashboard.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTableDTO {

    private String studentName;
    private String teacherName;
    private LocalDate date;
    private LocalTime time;
    private Double duration;
    private String location;
    private String status;

    public AppointmentTableDTO() { }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Double getDuration() {
        return duration;
    }
    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
