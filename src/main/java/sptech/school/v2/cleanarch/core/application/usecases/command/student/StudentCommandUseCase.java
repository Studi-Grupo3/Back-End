package sptech.school.v2.cleanarch.core.application.usecases.command.student;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.email.EmailSenderUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.password.PasswordResetTokenUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.student.StudentQueryUseCase;
import sptech.school.v2.cleanarch.domain.entities.PasswordResetToken;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.exception.AuthenticationException;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentCommandGateway;
import sptech.school.v2.cleanarch.domain.exception.EmailException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StudentCommandUseCase {
    private final StudentCommandGateway studentCommandGateway;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenUseCase passwordResetTokenUseCase;
    private final EmailSenderUseCase emailSenderUseCase;
    private final StudentQueryUseCase studentQueryUseCase;

    public StudentCommandUseCase(StudentCommandGateway studentCommandGateway, PasswordEncoder passwordEncoder, PasswordResetTokenUseCase passwordResetTokenUseCase, EmailSenderUseCase emailSenderUseCase, StudentQueryUseCase studentQueryUseCase) {
        this.studentCommandGateway = studentCommandGateway;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenUseCase = passwordResetTokenUseCase;
        this.emailSenderUseCase = emailSenderUseCase;
        this.studentQueryUseCase = studentQueryUseCase;
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
        Optional<Student> foundStudent = studentQueryUseCase.findByEmail(email);
        if (foundStudent.isPresent() && passwordEncoder.matches(password, foundStudent.get().getPassword())) {
            return foundStudent.get();
        }
        throw new AuthenticationException("Invalid credentials");
    }

    public void sendResetCode(String email) {
        if (email == null || email.isEmpty()) {
            throw new EmailException("O e-mail não pode ser nulo ou vazio.");
        }

        Optional<Student> optionalStudent = studentQueryUseCase.findByEmail(email);

        if (optionalStudent.isEmpty()) {
            throw new UserNullException("Usuário não encontrado com o e-mail: " + email);
        }

        String code = generateCode();
        PasswordResetToken token = new PasswordResetToken(email, code, LocalDateTime.now().plusMinutes(10));
        passwordResetTokenUseCase.save(token);

        emailSenderUseCase.sendEmail(email, "Código de Redefinição de Senha", "Seu código é: " + code);
    }

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);  // Gera um código de 6 dígitos
    }

    public void resetPassword(String email, String newPassword) {
        Optional<Student> optionalStudent = studentQueryUseCase.findByEmail(email);
        if (optionalStudent.isEmpty()) {
            throw new UserNullException("Usuário não encontrado com o e-mail: " + email);
        }
        Student student = optionalStudent.get();
        student.setPassword(passwordEncoder.encode(newPassword));
        studentCommandGateway.update(student);
    }
}