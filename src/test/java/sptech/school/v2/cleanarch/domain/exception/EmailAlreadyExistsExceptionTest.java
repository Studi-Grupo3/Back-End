package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("EmailAlreadyExistsException Tests")
class EmailAlreadyExistsExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "Email already exists";
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "Email already registered";

        try {
            throw new EmailAlreadyExistsException(message);
        } catch (EmailAlreadyExistsException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        EmailAlreadyExistsException exception = new EmailAlreadyExistsException("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}

