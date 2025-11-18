package sptech.school.v2.cleanarch.core.dtos.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VerifyCodeRequest Tests")
class VerifyCodeRequestTest {

    private VerifyCodeRequest verifyCodeRequest;

    @BeforeEach
    void setUp() {
        verifyCodeRequest = new VerifyCodeRequest();
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetGetEmail() {
        verifyCodeRequest.setEmail("user@email.com");
        assertEquals("user@email.com", verifyCodeRequest.getEmail());
    }

    @Test
    @DisplayName("Should set and get code")
    void testSetGetCode() {
        verifyCodeRequest.setCode("123456");
        assertEquals("123456", verifyCodeRequest.getCode());
    }

    @Test
    @DisplayName("Should initialize with null values")
    void testInitialValues() {
        VerifyCodeRequest request = new VerifyCodeRequest();
        assertNull(request.getEmail());
        assertNull(request.getCode());
    }

    @Test
    @DisplayName("Should set multiple times")
    void testSetMultipleTimes() {
        verifyCodeRequest.setEmail("first@email.com");
        assertEquals("first@email.com", verifyCodeRequest.getEmail());

        verifyCodeRequest.setEmail("second@email.com");
        assertEquals("second@email.com", verifyCodeRequest.getEmail());
    }

    @Test
    @DisplayName("Should set both email and code")
    void testSetBoth() {
        verifyCodeRequest.setEmail("user@email.com");
        verifyCodeRequest.setCode("654321");

        assertEquals("user@email.com", verifyCodeRequest.getEmail());
        assertEquals("654321", verifyCodeRequest.getCode());
    }
}

