package sptech.school.v2.cleanarch.core.application.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.exception.AuthenticationException;
import sptech.school.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.gateways.TeacherCommandGateway;

import java.util.Optional;

@Service
public class TeacherCommandUseCase {
    private final TeacherCommandGateway teacherCommandGateway;
    private final PasswordEncoder passwordEncoder;

    public TeacherCommandUseCase(TeacherCommandGateway teacherCommandGateway, PasswordEncoder passwordEncoder) {
        this.teacherCommandGateway = teacherCommandGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Teacher create(Teacher teacher) {
        if (teacher == null) throw new UserNullException("The user cannot be null.");

        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

        return teacherCommandGateway.save(teacher);
    }

    public Teacher update(Teacher teacher) {
        return teacherCommandGateway.update(teacher);
    }

    public void delete(Integer id) {
        if (id == null) throw new UserNullException("The user ID cannot be null.");
        if (id <= 0) throw new UserNullException("The user ID must be greater than zero.");
        teacherCommandGateway.delete(id);
    }

    public Teacher login(String email, String password) {
        Optional<Teacher> foundTeacher = teacherCommandGateway.findByEmail(email);
        if (foundTeacher.isPresent() && passwordEncoder.matches(password, foundTeacher.get().getPassword())) {
            return foundTeacher.get();
        }
        throw new AuthenticationException("Invalid credentials");
    }
}