package sptech.school.v2.cleanarch.core.application.utils;

import org.springframework.stereotype.Component;
import sptech.school.domain.entity.User;
import sptech.school.domain.exception.EmailAlreadyExistsException;
import sptech.school.v2.cleanarch.core.application.gateways.student.StudentQueryGateway;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.v2.cleanarch.domain.exception.CpfAlreadyExistsException;

@Component
public class VerifyEmailAndCpfUtil {
    private final TeacherQueryGateway teacherQueryGateway;
    private final StudentQueryGateway studentQueryGateway;

    public VerifyEmailAndCpfUtil(TeacherQueryGateway teacherQueryGateway, StudentQueryGateway studentQueryGateway) {
        this.teacherQueryGateway = teacherQueryGateway;
        this.studentQueryGateway = studentQueryGateway;
    }

    public void verify(User user) {
        if (studentQueryGateway.studentExistsByEmail(user.getEmail()) || teacherQueryGateway.teacherExistsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        if (studentQueryGateway.studentExistsByCpf(user.getCpf()) || teacherQueryGateway.teacherExistsByCpf(user.getCpf())) {
            throw new CpfAlreadyExistsException("CPF already registered");
        }
    }
}
