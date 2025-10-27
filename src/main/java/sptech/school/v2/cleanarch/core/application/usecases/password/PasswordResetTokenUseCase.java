package sptech.school.v2.cleanarch.core.application.usecases.password;

import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.domain.entities.PasswordResetToken;
import sptech.school.v2.cleanarch.core.application.gateways.password.PasswordResetTokenGateway;

import java.util.Optional;

@Service
public class PasswordResetTokenUseCase {
    private final PasswordResetTokenGateway tokenGateway;

    public PasswordResetTokenUseCase(PasswordResetTokenGateway tokenGateway) {
        this.tokenGateway = tokenGateway;
    }

    public Optional<PasswordResetToken> findByToken(String token) {
        return tokenGateway.findByToken(token);
    }

    public PasswordResetToken save(PasswordResetToken token) {
        return tokenGateway.save(token);
    }
}