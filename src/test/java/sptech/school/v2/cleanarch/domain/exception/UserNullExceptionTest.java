package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("UserNullException Tests")
class UserNullExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "User cannot be null";
        UserNullException exception = new UserNullException(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "User is null";

        try {
            throw new UserNullException(message);
        } catch (UserNullException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        UserNullException exception = new UserNullException("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}


