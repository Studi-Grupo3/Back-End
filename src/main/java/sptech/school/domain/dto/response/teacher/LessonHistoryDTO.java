package sptech.school.domain.dto.response.teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LessonHistoryDTO {
    private Integer id;
    private String subject;
    private Integer studentId;
    private String studentName;
    private LocalDate date;
    private LocalTime time;
    private Double duration;      // em minutos
    private String location;
    private Boolean online;
    private String status;        // "COMPLETED" ou "CANCELLED"
    private Double totalValue;
    private String motivoCancelamento;    // opcional, se armazenado
    private LocalDateTime cancelDateTime; // opcional, se armazenado

    public LessonHistoryDTO() { }

    public LessonHistoryDTO(Integer id, String subject, Integer studentId, String studentName,
                            LocalDate date, LocalTime time, Double duration,
                            String location, Boolean online, String status, Double totalValue,
                            String motivoCancelamento, LocalDateTime cancelDateTime) {
        this.id = id;
        this.subject = subject;
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.location = location;
        this.online = online;
        this.status = status;
        this.totalValue = totalValue;
        this.motivoCancelamento = motivoCancelamento;
        this.cancelDateTime = cancelDateTime;
    }

    // Getters e setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Integer getStudentId() {
        return studentId;
    }
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
    public Boolean getOnline() {
        return online;
    }
    public void setOnline(Boolean online) {
        this.online = online;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }
    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }
    public LocalDateTime getCancelDateTime() {
        return cancelDateTime;
    }
    public void setCancelDateTime(LocalDateTime cancelDateTime) {
        this.cancelDateTime = cancelDateTime;
    }
}
