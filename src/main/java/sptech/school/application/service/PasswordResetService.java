package sptech.school.application.service;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.infra.persistence.repository.PasswordResetTokenRepository;
import sptech.school.v2.cleanarch.core.application.gateways.EmailSenderGateway;

import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final EmailSenderGateway emailSenderGateway;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, EmailSenderGateway emailSenderGateway) {
        this.tokenRepository = tokenRepository;
        this.emailSenderGateway = emailSenderGateway;
    }

    public boolean verifyCode(String email, String code) {
        return tokenRepository.findByToken(code)
                .filter(token -> token.getEmail().equals(email))
                .filter(token -> token.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }

}