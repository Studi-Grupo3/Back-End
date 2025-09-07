package sptech.school.v2.cleanarch.domain.entities;

import jakarta.persistence.*;
import sptech.school.domain.enumerated.Subject;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_teacher")
public class Teacher extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Subject> subjects;
    private Double hourlyRate;
    private String resumeTeacher;
    private String yearsExperience;
    private String academicFormation;

    public Teacher() {
    }

    public Teacher(String name, String email, String cpf, String password, List<Subject> subjects) {
        super(name, email, cpf, password);
        this.subjects = new ArrayList<>();
    }

    public String getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(String yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getAcademicFormation() {
        return academicFormation;
    }

    public void setAcademicFormation(String academicFormation) {
        this.academicFormation = academicFormation;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", subjects='" + subjects + '\'' +
                '}';
    }

    public String getResumeTeacher() {
        return resumeTeacher;
    }

    public void setResumeTeacher(String resumeTeacher) {
        this.resumeTeacher = resumeTeacher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subject) {
        this.subjects = subject;
    }
}