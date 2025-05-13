package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {

    Optional<PasswordResetToken> findByToken(String token);

}
