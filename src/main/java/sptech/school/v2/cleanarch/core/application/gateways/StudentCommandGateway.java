package sptech.school.v2.cleanarch.core.application.gateways;

import sptech.school.domain.entity.Student;

import java.util.Optional;

public interface StudentCommandGateway {
    Student save(Student student);
    Student update(Student student);
    void delete(Integer id);
    Optional<Student> findByEmail(String email);
}
