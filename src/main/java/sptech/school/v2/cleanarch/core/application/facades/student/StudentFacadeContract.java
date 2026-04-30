package sptech.school.v2.cleanarch.core.application.facades.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.dtos.out.ResourceFileResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.Student;

import java.io.IOException;

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
    void resetPassword(String email, String newPassword);
    ResourceFileResponseDTO uploadProfileImage(MultipartFile file, Integer id) throws IOException;
    ResourceFile getProfileImage(Integer id) throws IOException;
}