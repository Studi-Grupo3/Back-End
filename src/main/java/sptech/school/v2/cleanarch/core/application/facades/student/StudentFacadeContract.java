package sptech.school.v2.cleanarch.core.application.facades.student;

import sptech.school.domain.entity.Student;

import java.util.List;

public interface StudentFacadeContract {
    Student create(Student student);
    List<Student> listAll();
    Student update(Student student, Integer id);
    void delete(Integer id);
    Student findById(Integer id);
    Boolean studentExistsByEmail(String email);
    Boolean studentExistsByCpf(String cpf);
}