package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LessonHistoryDTO {
    private Integer id;
    private String subject;
    private Integer studentId;
    private String studentName;
    private String studentPhone;
    private String studentImageUrl;
    private LocalDate date;
    private LocalTime time;
    private Double duration;      // em minutos
    private String location;
    private Boolean online;
    private String status;        // "COMPLETED" ou "CANCELLED"
    private Double totalValue;
    private String motivoCancelamento;    // opcional, se armazenado
    private LocalDateTime cancelDateTime; // opcional, se armazenado
    private String studentAddress;
    private String responsibleName;
    private String responsiblePhone;
    private Integer studentAge;
    private Boolean isAdult;
    private String phase;
    private String schoolGrade;

    public LessonHistoryDTO() { }

    public LessonHistoryDTO(Integer id, String subject, Integer studentId, String studentName,
                            String studentPhone,
                            String studentImageUrl,
                            LocalDate date, LocalTime time, Double duration,
                            String location, Boolean online, String status, Double totalValue,
                            String motivoCancelamento, LocalDateTime cancelDateTime,
                            String studentAddress, String responsibleName, String responsiblePhone,
                            Integer studentAge, Boolean isAdult, String phase, String schoolGrade) {
        this.id = id;
        this.subject = subject;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.studentImageUrl = studentImageUrl;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.location = location;
        this.online = online;
        this.status = status;
        this.totalValue = totalValue;
        this.motivoCancelamento = motivoCancelamento;
        this.cancelDateTime = cancelDateTime;
        this.studentAddress = studentAddress;
        this.responsibleName = responsibleName;
        this.responsiblePhone = responsiblePhone;
        this.studentAge = studentAge;
        this.isAdult = isAdult;
        this.phase = phase;
        this.schoolGrade = schoolGrade;
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
    public String getStudentPhone() { return studentPhone; }
    public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }
    public String getStudentImageUrl() { return studentImageUrl; }
    public void setStudentImageUrl(String studentImageUrl) { this.studentImageUrl = studentImageUrl; }
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
    public String getStudentAddress() { return studentAddress; }
    public void setStudentAddress(String studentAddress) { this.studentAddress = studentAddress; }
    public String getResponsibleName() { return responsibleName; }
    public void setResponsibleName(String responsibleName) { this.responsibleName = responsibleName; }
    public String getResponsiblePhone() { return responsiblePhone; }
    public void setResponsiblePhone(String responsiblePhone) { this.responsiblePhone = responsiblePhone; }
    public Integer getStudentAge() { return studentAge; }
    public void setStudentAge(Integer studentAge) { this.studentAge = studentAge; }
    public Boolean getIsAdult() { return isAdult; }
    public void setIsAdult(Boolean isAdult) { this.isAdult = isAdult; }
    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public String getSchoolGrade() { return schoolGrade; }
    public void setSchoolGrade(String schoolGrade) { this.schoolGrade = schoolGrade; }
}
