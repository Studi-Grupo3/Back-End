package sptech.school.v2.cleanarch.core.application.facades.student;

import sptech.school.v2.cleanarch.domain.entities.Student;

import java.util.List;

public interface StudentFacadeContract {
    Student create(Student student);
    List<Student> listAll();
    Student update(Student student, Integer id);
    void delete(Integer id);
    Student findById(Integer id);
    Boolean studentExistsByEmail(String email);
    Boolean studentExistsByCpf(String cpf);
    Student login(String email, String password);
    void sendResetCode(String email);
}