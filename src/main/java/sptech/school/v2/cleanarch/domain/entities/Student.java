package sptech.school.v2.cleanarch.domain.entities;

import jakarta.persistence.*;
import sptech.school.v2.cleanarch.domain.Responsible;

@Entity
@Table(name = "tb_student")
public class Student extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String schoolGrade;

    private String schoolName;

    @Embedded
    private Responsible responsible;

    public Student() {
    }

    public Student(String name, String email, String cpf, String password) {
        super(name, email, cpf, password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolGrade() {
        return schoolGrade;
    }

    public void setSchoolGrade(String schoolGrade) {
        this.schoolGrade = schoolGrade;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }
}