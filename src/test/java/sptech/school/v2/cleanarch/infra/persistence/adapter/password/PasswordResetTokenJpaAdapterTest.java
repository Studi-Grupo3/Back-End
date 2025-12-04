package sptech.school.v2.cleanarch.infra.persistence.adapter.password;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.v2.cleanarch.domain.entities.PasswordResetToken;
import sptech.school.v2.cleanarch.infra.persistence.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PasswordResetTokenJpaAdapter Tests")
@ExtendWith(MockitoExtension.class)
class PasswordResetTokenJpaAdapterTest {

    private PasswordResetTokenJpaAdapter passwordResetTokenJpaAdapter;

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private PasswordResetToken token;

    @BeforeEach
    void setUp() {
        passwordResetTokenJpaAdapter = new PasswordResetTokenJpaAdapter(passwordResetTokenRepository);

        LocalDateTime expiresAt = LocalDateTime.of(2025, 12, 25, 10, 0);
        token = new PasswordResetToken("user@email.com", "abc123token", expiresAt);
    }

    @Test
    @DisplayName("Should find token by token string")
    void testFindByToken() {
        when(passwordResetTokenRepository.findByToken("abc123token")).thenReturn(Optional.of(token));

        Optional<PasswordResetToken> result = passwordResetTokenJpaAdapter.findByToken("abc123token");

        assertTrue(result.isPresent());
        assertEquals("user@email.com", result.get().getEmail());
        verify(passwordResetTokenRepository, times(1)).findByToken("abc123token");
    }

    @Test
    @DisplayName("Should return empty optional when token not found")
    void testFindByTokenNotFound() {
        when(passwordResetTokenRepository.findByToken("invalid")).thenReturn(Optional.empty());

        Optional<PasswordResetToken> result = passwordResetTokenJpaAdapter.findByToken("invalid");

        assertFalse(result.isPresent());
        verify(passwordResetTokenRepository, times(1)).findByToken("invalid");
    }

    @Test
    @DisplayName("Should save a password reset token")
    void testSaveToken() {
        when(passwordResetTokenRepository.save(token)).thenReturn(token);

        PasswordResetToken result = passwordResetTokenJpaAdapter.save(token);

        assertNotNull(result);
        assertEquals("user@email.com", result.getEmail());
        assertEquals("abc123token", result.getToken());
        verify(passwordResetTokenRepository, times(1)).save(token);
    }

    @Test
    @DisplayName("Should save token with different email")
    void testSaveTokenWithDifferentEmail() {
        LocalDateTime expiresAt = LocalDateTime.of(2025, 12, 31, 23, 59);
        PasswordResetToken newToken = new PasswordResetToken("another@email.com", "xyz789token", expiresAt);

        when(passwordResetTokenRepository.save(newToken)).thenReturn(newToken);

        PasswordResetToken result = passwordResetTokenJpaAdapter.save(newToken);

        assertNotNull(result);
        assertEquals("another@email.com", result.getEmail());
        verify(passwordResetTokenRepository, times(1)).save(newToken);
    }

    @Test
    @DisplayName("Should handle null token in save")
    void testSaveNullToken() {
        when(passwordResetTokenRepository.save(null)).thenThrow(new IllegalArgumentException("Token cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> {
            passwordResetTokenJpaAdapter.save(null);
        });
    }

    @Test
    @DisplayName("Should find token by different token strings")
    void testFindByMultipleTokens() {
        PasswordResetToken token1 = new PasswordResetToken("user1@email.com", "token1", LocalDateTime.now());
        PasswordResetToken token2 = new PasswordResetToken("user2@email.com", "token2", LocalDateTime.now());

        when(passwordResetTokenRepository.findByToken("token1")).thenReturn(Optional.of(token1));
        when(passwordResetTokenRepository.findByToken("token2")).thenReturn(Optional.of(token2));

        Optional<PasswordResetToken> result1 = passwordResetTokenJpaAdapter.findByToken("token1");
        Optional<PasswordResetToken> result2 = passwordResetTokenJpaAdapter.findByToken("token2");

        assertTrue(result1.isPresent());
        assertTrue(result2.isPresent());
        assertEquals("user1@email.com", result1.get().getEmail());
        assertEquals("user2@email.com", result2.get().getEmail());
    }
}

