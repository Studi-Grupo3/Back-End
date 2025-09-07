package sptech.school.v2.cleanarch.core.application.gateways;

import sptech.school.domain.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenGateway {
    Optional<PasswordResetToken> findByToken(String token);

    PasswordResetToken save(PasswordResetToken token);
}
