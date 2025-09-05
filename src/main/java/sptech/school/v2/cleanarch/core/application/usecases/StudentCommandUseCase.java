package sptech.school.v2.cleanarch.core.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Student;
import sptech.school.domain.exception.AuthenticationException;
import sptech.school.v2.cleanarch.core.application.gateways.StudentCommandGateway;

import java.util.Optional;

@Service
public class StudentCommandUseCase {
    private final StudentCommandGateway studentCommandGateway;
    private final PasswordEncoder passwordEncoder;

    public StudentCommandUseCase(StudentCommandGateway studentCommandGateway, PasswordEncoder passwordEncoder) {
        this.studentCommandGateway = studentCommandGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Student create(Student student) {
        if (student == null) throw new IllegalArgumentException("The student cannot be null.");

        student.setPassword(passwordEncoder.encode(student.getPassword()));

        return studentCommandGateway.save(student);
    }

    public Student update(Student student) {
        return studentCommandGateway.update(student);
    }

    public void delete(Integer id) {
        if (id == null) throw new IllegalArgumentException("The student ID cannot be null.");
        if (id <= 0) throw new IllegalArgumentException("The student ID must be greater than zero.");
        studentCommandGateway.delete(id);
    }

    public Student login(String email, String password) {
        Optional<Student> foundStudent = studentCommandGateway.findByEmail(email);
        if (foundStudent.isPresent() && passwordEncoder.matches(password, foundStudent.get().getPassword())) {
            return foundStudent.get();
        }
        throw new AuthenticationException("Invalid credentials");
    }
}