package sptech.school.v2.cleanarch.core.application.facades.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.v2.cleanarch.domain.entities.Student;

public interface StudentFacadeContract {
    Student create(Student student);
    Page<Student> listAll(Pageable pageable);
    Student update(Student student, Integer id);
    void delete(Integer id);
    Student findById(Integer id);
    Boolean studentExistsByEmail(String email);
    Boolean studentExistsByCpf(String cpf);
    Student login(String email, String password);
    void sendResetCode(String email);
}