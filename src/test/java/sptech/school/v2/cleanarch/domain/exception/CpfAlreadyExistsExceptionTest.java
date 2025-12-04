package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("CpfAlreadyExistsException Tests")
class CpfAlreadyExistsExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "CPF already exists";
        CpfAlreadyExistsException exception = new CpfAlreadyExistsException(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "CPF already registered";

        try {
            throw new CpfAlreadyExistsException(message);
        } catch (CpfAlreadyExistsException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        CpfAlreadyExistsException exception = new CpfAlreadyExistsException("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}

