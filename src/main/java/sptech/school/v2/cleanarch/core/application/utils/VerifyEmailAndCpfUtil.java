package sptech.school.v2.cleanarch.core.application.utils;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.v2.cleanarch.domain.entities.User;
import sptech.school.v2.cleanarch.domain.exception.CpfAlreadyExistsException;
import sptech.school.v2.cleanarch.domain.exception.EmailAlreadyExistsException;

import java.util.Optional;
import java.util.Objects;

@Component
public class VerifyEmailAndCpfUtil {
    private final TeacherQueryGateway teacherQueryGateway;
    private final StudentQueryGateway studentQueryGateway;

    public VerifyEmailAndCpfUtil(TeacherQueryGateway teacherQueryGateway, StudentQueryGateway studentQueryGateway) {
        this.teacherQueryGateway = teacherQueryGateway;
        this.studentQueryGateway = studentQueryGateway;
    }

    public void verify(User user) {
        Objects.requireNonNull(user, "user must not be null");

        String email = user.getEmail();
        String cpf = user.getCpf();

        if (email != null && !email.isBlank()) {
            verifyEmail(email);
        }

        if (cpf != null && !cpf.isBlank()) {
            verifyCpf(cpf);
        }
    }

    public void verify(User user, Integer id) {
        Objects.requireNonNull(user, "user must not be null");

        String email = user.getEmail();
        String cpf = user.getCpf();

        if (email != null && !email.isBlank()) {
            checkIfExists(email, id, "Email");
        }

        if (cpf != null && !cpf.isBlank()) {
            checkIfExists(cpf, id, "CPF");
        }
    }

    private void verifyEmail(String email) {
        Optional<Integer> studentId = studentQueryGateway.findIdByEmail(email);
        Optional<Integer> teacherId = teacherQueryGateway.findIdByEmail(email);

        if (studentId.isPresent() || teacherId.isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
    }

    private void verifyCpf(String cpf) {
        Optional<Integer> studentId = studentQueryGateway.findByCpf(cpf);
        Optional<Integer> teacherId = teacherQueryGateway.findByCpf(cpf);

        if (studentId.isPresent() || teacherId.isPresent()) {
            throw new CpfAlreadyExistsException("CPF already registered");
        }
    }

    private void checkIfExists(String value, Integer currentUserId, String fieldType) {
        Optional<Integer> studentId;
        Optional<Integer> teacherId;

        if (fieldType.equals("Email")) {
            studentId = studentQueryGateway.findIdByEmail(value);
            teacherId = teacherQueryGateway.findIdByEmail(value);
        } else {
            studentId = studentQueryGateway.findByCpf(value);
            teacherId = teacherQueryGateway.findByCpf(value);
        }

        boolean existsInStudent = studentId.isPresent() && !studentId.get().equals(currentUserId);
        boolean existsInTeacher = teacherId.isPresent() && !teacherId.get().equals(currentUserId);

        if (existsInStudent || existsInTeacher) {
            if (fieldType.equals("Email")) {
                throw new EmailAlreadyExistsException("Email already registered");
            } else {
                throw new CpfAlreadyExistsException("CPF already registered");
            }
        }
    }
}
