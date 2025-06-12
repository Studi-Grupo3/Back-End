package sptech.school.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import sptech.school.domain.enumerated.Subject;

@Entity
@Table(name = "tb_teacher")
public class Teacher extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Subject subject;
    private Double hourlyRate;
    private String resumeTeacher;

    public Teacher() {
    }

    public Teacher(String name, String email, String cpf, String password, Subject subject) {
        super(name, email, cpf, password);
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}