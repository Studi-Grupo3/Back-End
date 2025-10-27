package sptech.school.v2.cleanarch.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_content")
public class Content extends ResourceFile {
    @ManyToOne
    @JoinColumn(name = "fkStudent", nullable = false)
    @NotNull
    private Student student;

    public Content() {
    }

    public Content(String fileName, String fileType, String fileLocation, Long fileSize) {
        super(fileName, fileType, fileLocation, fileSize);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
