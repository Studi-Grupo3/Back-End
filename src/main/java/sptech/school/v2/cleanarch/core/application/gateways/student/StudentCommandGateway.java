package sptech.school.v2.cleanarch.core.application.gateways.student;

import sptech.school.v2.cleanarch.domain.entities.Student;

public interface StudentCommandGateway {
    Student save(Student student);
    Student update(Student student);
    void delete(Integer id);
}
