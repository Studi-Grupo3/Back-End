package sptech.school.v2.cleanarch.core.dtos.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("ForgotPasswordRequest Tests")
class ForgotPasswordRequestTest {

    private ForgotPasswordRequest forgotPasswordRequest;

    @BeforeEach
    void setUp() {
        forgotPasswordRequest = new ForgotPasswordRequest();
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetGetEmail() {
        forgotPasswordRequest.setEmail("user@email.com");
        assertEquals("user@email.com", forgotPasswordRequest.getEmail());
    }

    @Test
    @DisplayName("Should initialize with null email")
    void testInitialValue() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        assertNull(request.getEmail());
    }

    @Test
    @DisplayName("Should change email multiple times")
    void testChangeEmail() {
        forgotPasswordRequest.setEmail("first@email.com");
        assertEquals("first@email.com", forgotPasswordRequest.getEmail());

        forgotPasswordRequest.setEmail("second@email.com");
        assertEquals("second@email.com", forgotPasswordRequest.getEmail());
    }

    @Test
    @DisplayName("Should handle null email")
    void testNullEmail() {
        forgotPasswordRequest.setEmail(null);
        assertNull(forgotPasswordRequest.getEmail());
    }

    @Test
    @DisplayName("Should handle empty string email")
    void testEmptyEmail() {
        forgotPasswordRequest.setEmail("");
        assertEquals("", forgotPasswordRequest.getEmail());
    }
}

