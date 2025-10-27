package sptech.school.v2.cleanarch.core.application.gateways.password;

import sptech.school.v2.cleanarch.domain.entities.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenGateway {
    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken save(PasswordResetToken token);
}
