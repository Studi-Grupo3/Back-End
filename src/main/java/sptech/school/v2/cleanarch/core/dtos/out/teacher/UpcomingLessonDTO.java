package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import java.time.LocalDate;
import java.time.LocalTime;

public class UpcomingLessonDTO {
    private Integer id;
    private String disciplina;     // ex.: nome da disciplina do Teacher
    private Integer studentId;
    private String studentName;
    private String studentPhone;
    private LocalDate date;        // data da aula
    private LocalTime time;        // horário da aula
    private Double lessonDuration; // em minutos
    private String location;       // string de localização/link
    private String status;         // status da appointment

    public UpcomingLessonDTO() { }

    public UpcomingLessonDTO(Integer id, String disciplina, Integer studentId, String studentName,
                             String studentPhone,
                             LocalDate date, LocalTime time, Double lessonDuration,
                             String location, String status) {
        this.id = id;
        this.disciplina = disciplina;
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentPhone = studentPhone;
        this.date = date;
        this.time = time;
        this.lessonDuration = lessonDuration;
        this.location = location;
        this.status = status;
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
}
