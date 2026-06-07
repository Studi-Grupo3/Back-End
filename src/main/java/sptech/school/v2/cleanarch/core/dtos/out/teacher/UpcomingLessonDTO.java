package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpcomingLessonDTO {
    private Integer id;
    private String disciplina;     // ex.: nome da disciplina do Teacher
    private Integer studentId;
    private String studentName;
    private String studentPhone;
    private String studentImageUrl;
    private LocalDate date;        // data da aula
    private LocalTime time;        // horário da aula
    private Double lessonDuration; // em minutos
    private String location;       // string de localização/link
    private String status;         // status da appointment
    private String studentAddress;
    private String responsibleName;
    private String responsiblePhone;
    private Integer studentAge;
    private Boolean isAdult;
    private String phase;
    private String schoolGrade;

    public UpcomingLessonDTO() { }

    public UpcomingLessonDTO(Integer id, String disciplina, Integer studentId, String studentName,
                             String studentPhone,
                             String studentImageUrl,
                             LocalDate date, LocalTime time, Double lessonDuration,
                             String location, String status,
                             String studentAddress, String responsibleName, String responsiblePhone,
                             Integer studentAge, Boolean isAdult, String phase, String schoolGrade) {
        this.id = id;
        this.disciplina = disciplina;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.studentImageUrl = studentImageUrl;
        this.date = date;
        this.time = time;
        this.lessonDuration = lessonDuration;
        this.location = location;
        this.status = status;
        this.studentAddress = studentAddress;
        this.responsibleName = responsibleName;
        this.responsiblePhone = responsiblePhone;
        this.studentAge = studentAge;
        this.isAdult = isAdult;
        this.phase = phase;
        this.schoolGrade = schoolGrade;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getStudentPhone() { return studentPhone; }
    public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }
    public String getStudentImageUrl() { return studentImageUrl; }
    public void setStudentImageUrl(String studentImageUrl) { this.studentImageUrl = studentImageUrl; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }
    public Double getLessonDuration() { return lessonDuration; }
    public void setLessonDuration(Double lessonDuration) { this.lessonDuration = lessonDuration; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
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
