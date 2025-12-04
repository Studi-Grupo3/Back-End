package sptech.school.v2.cleanarch.domain.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("UserDontHaveProfilePhoto Tests")
class UserDontHaveProfilePhotoTest {

    @Test
    @DisplayName("Should create exception with message")
    void testExceptionWithMessage() {
        String message = "User does not have a profile photo";
        UserDontHaveProfilePhoto exception = new UserDontHaveProfilePhoto(message);

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Should throw exception when instantiated")
    void testExceptionThrow() {
        String message = "Photo not found";

        try {
            throw new UserDontHaveProfilePhoto(message);
        } catch (UserDontHaveProfilePhoto e) {
            assertNotNull(e);
        }
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void testExceptionHierarchy() {
        UserDontHaveProfilePhoto exception = new UserDontHaveProfilePhoto("Test");

        assertNotNull(exception);
        assertTrue(exception instanceof RuntimeException);
    }
}
