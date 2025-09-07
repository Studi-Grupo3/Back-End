package sptech.school.v2.cleanarch.core.application.usecases;

import org.springframework.stereotype.Service;
import sptech.school.domain.entity.PasswordResetToken;
import sptech.school.v2.cleanarch.core.application.gateways.PasswordResetTokenGateway;

import java.util.Optional;

@Service
public class PasswordResetTokenUseCase {
    private final PasswordResetTokenGateway tokenGateway;

    public PasswordResetTokenUseCase(PasswordResetTokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    Optional<PasswordResetToken> findByToken(String token) {
        return tokenGateway.findByToken(token);
    }

    PasswordResetToken save(PasswordResetToken token) {
        return tokenGateway.save(token);
    }
}