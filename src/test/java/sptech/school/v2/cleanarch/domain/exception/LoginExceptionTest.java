package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("LoginException Tests")
class LoginExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "Invalid credentials";
        LoginException exception = new LoginException(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "Invalid login attempt";

        try {
            throw new LoginException(message);
        } catch (LoginException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        LoginException exception = new LoginException("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}

