package sptech.school.v2.cleanarch.core.application.usecases.command.teacher;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.usecases.email.EmailSenderUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.password.PasswordResetTokenUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.teacher.TeacherQueryUseCase;
import sptech.school.v2.cleanarch.domain.entities.PasswordResetToken;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.exception.AuthenticationException;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherCommandGateway;
import sptech.school.v2.cleanarch.domain.exception.EmailException;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class TeacherCommandUseCase {
    private final TeacherCommandGateway teacherCommandGateway;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderUseCase emailSenderUseCase;
    private final PasswordResetTokenUseCase passwordResetTokenUseCase;
    private final TeacherQueryUseCase teacherQueryUseCase;

    public TeacherCommandUseCase(TeacherCommandGateway teacherCommandGateway, PasswordEncoder passwordEncoder, EmailSenderUseCase emailSenderUseCase, PasswordResetTokenUseCase passwordResetTokenUseCase, TeacherQueryUseCase teacherQueryUseCase) {
        this.teacherCommandGateway = teacherCommandGateway;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderUseCase = emailSenderUseCase;
        this.passwordResetTokenUseCase = passwordResetTokenUseCase;
        this.teacherQueryUseCase = teacherQueryUseCase;
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
        Optional<Teacher> foundTeacher = teacherQueryUseCase.findByEmail(email);
        if (foundTeacher.isPresent() && passwordEncoder.matches(password, foundTeacher.get().getPassword())) {
            return foundTeacher.get();
        }
        throw new AuthenticationException("Invalid credentials");
    }


    public void sendResetCode(String email) {
        if (email == null || email.isEmpty()) {
            throw new EmailException("O e-mail não pode ser nulo ou vazio.");
        }

        Optional<Teacher> optionalTeacher = teacherQueryUseCase.findByEmail(email);

        if (optionalTeacher.isEmpty()) {
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
}