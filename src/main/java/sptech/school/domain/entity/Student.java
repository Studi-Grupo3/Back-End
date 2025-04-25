package sptech.school.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_student")
public class Student extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String responsibleCellphone;

    public Student() {
    }

    public Student(String name, String email, String cpf, String password, String responsibleCellphone) {
        super(name, email, cpf, password);
        this.responsibleCellphone = responsibleCellphone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponsibleCellphone() {
        return responsibleCellphone;
    }

    public void setResponsibleCellphone(String responsibleCellphone) {
        this.responsibleCellphone = responsibleCellphone;
    }
}