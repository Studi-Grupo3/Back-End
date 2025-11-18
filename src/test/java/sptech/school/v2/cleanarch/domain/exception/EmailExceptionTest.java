package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("EmailException Tests")
class EmailExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "Email sending failed";
        EmailException exception = new EmailException(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "SMTP error";

        try {
            throw new EmailException(message);
        } catch (EmailException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        EmailException exception = new EmailException("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}
