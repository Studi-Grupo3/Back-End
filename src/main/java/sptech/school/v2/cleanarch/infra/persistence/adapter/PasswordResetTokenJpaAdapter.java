package sptech.school.v2.cleanarch.infra.persistence.adapter;

import org.springframework.stereotype.Component;
import sptech.school.domain.entity.PasswordResetToken;
import sptech.school.v2.cleanarch.core.application.gateways.PasswordResetTokenGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.PasswordResetTokenRepository;

import java.util.Optional;

@Component
public class PasswordResetTokenJpaAdapter implements PasswordResetTokenGateway {
    private final PasswordResetTokenRepository repository;

    public PasswordResetTokenJpaAdapter(PasswordResetTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken token) {
        return repository.save(token);
    }
}
