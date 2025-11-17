package sptech.school.v2.cleanarch.core.application.utils;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.v2.cleanarch.domain.entities.User;
import sptech.school.v2.cleanarch.domain.exception.CpfAlreadyExistsException;
import sptech.school.v2.cleanarch.domain.exception.EmailAlreadyExistsException;

import java.util.Optional;
import java.util.function.Supplier;
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

        Long userId = user.getId();
        String email = user.getEmail();
        String cpf = user.getCpf();

        if (email != null && !email.isBlank()) {
            checkAlreadyExists(
                studentQueryGateway.findIdByEmail(email),
                teacherQueryGateway.findIdByEmail(email),
                userId,
                () -> new EmailAlreadyExistsException("Email already registered")
            );
        }

        if (cpf != null && !cpf.isBlank()) {
            checkAlreadyExists(
                studentQueryGateway.findIdByCpf(cpf),
                teacherQueryGateway.findIdByCpf(cpf),
                userId,
                () -> new CpfAlreadyExistsException("CPF already registered")
            );
        }
    }

    private void checkAlreadyExists(
            Optional<Long> id1,
            Optional<Long> id2,
            Long currentUserId,
            Supplier<RuntimeException> exceptionSupplier) {

        if (existsForOtherUser(id1, currentUserId) ||
            existsForOtherUser(id2, currentUserId)) {
            throw exceptionSupplier.get();
        }
    }

    private boolean existsForOtherUser(Optional<Long> foundId, Long currentUserId) {
        return foundId.isPresent() &&
               (currentUserId == null || !foundId.get().equals(currentUserId));
    }
}
