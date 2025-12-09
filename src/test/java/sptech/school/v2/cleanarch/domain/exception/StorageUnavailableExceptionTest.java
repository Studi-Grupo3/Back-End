package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("StorageUnavailableException Tests")
class StorageUnavailableExceptionTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "Storage service is unavailable";
        StorageUnavailableException exception = new StorageUnavailableException(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "Connection timeout";

        try {
            throw new StorageUnavailableException(message);
        } catch (StorageUnavailableException e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        StorageUnavailableException exception = new StorageUnavailableException("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}
