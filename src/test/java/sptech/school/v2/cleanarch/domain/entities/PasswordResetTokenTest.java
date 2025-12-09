package sptech.school.v2.cleanarch.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PasswordResetToken Entity Tests")
class PasswordResetTokenTest {

    private PasswordResetToken passwordResetToken;

    @BeforeEach
    void setUp() {
        passwordResetToken = new PasswordResetToken();
    }

    @Test
    @DisplayName("Should create password reset token with all parameters")
    void testConstructor() {
        LocalDateTime expiresAt = LocalDateTime.of(2025, 12, 25, 10, 0);
        PasswordResetToken token = new PasswordResetToken("user@email.com", "abc123token", expiresAt);

        assertEquals("user@email.com", token.getEmail());
        assertEquals("abc123token", token.getToken());
        assertEquals(expiresAt, token.getExpiresAt());
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetGetEmail() {
        passwordResetToken.setEmail("reset@email.com");
        assertEquals("reset@email.com", passwordResetToken.getEmail());
    }

    @Test
    @DisplayName("Should set and get token")
    void testSetGetToken() {
        passwordResetToken.setToken("token123456");
        assertEquals("token123456", passwordResetToken.getToken());
    }

    @Test
    @DisplayName("Should set and get expires at")
    void testSetGetExpiresAt() {
        LocalDateTime expiresAt = LocalDateTime.of(2025, 12, 31, 23, 59);
        passwordResetToken.setExpiresAt(expiresAt);
        assertEquals(expiresAt, passwordResetToken.getExpiresAt());
    }

    @Test
    @DisplayName("Should create empty password reset token")
    void testEmptyConstructor() {
        PasswordResetToken token = new PasswordResetToken();
        assertNull(token.getEmail());
        assertNull(token.getToken());
        assertNull(token.getExpiresAt());
    }
}

