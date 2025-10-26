package sptech.school.v2.cleanarch.core.application.usecases.password;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.gateways.email.EmailSenderGateway;

import java.time.LocalDateTime;

@Service
public class PasswordResetUseCase {

    private final PasswordResetTokenUseCase passwordResetTokenUseCase;
    private final EmailSenderGateway emailSenderGateway;

    public PasswordResetUseCase(PasswordResetTokenUseCase passwordResetTokenUseCase, EmailSenderGateway emailSenderGateway) {
        this.passwordResetTokenUseCase = passwordResetTokenUseCase;
        this.emailSenderGateway = emailSenderGateway;
    }

    public boolean verifyCode(String email, String code) {
        return passwordResetTokenUseCase.findByToken(code)
                .filter(token -> token.getEmail().equals(email))
                .filter(token -> token.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }

}