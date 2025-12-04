package sptech.school.v2.cleanarch.core.dtos.out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ErrorResponseDTO Tests")
class ErrorResponseDTOTest {

    private ErrorResponseDTO errorResponseDTO;

    @BeforeEach
    void setUp() {
        errorResponseDTO = new ErrorResponseDTO(400, "Bad Request", "Invalid input", "trace details");
    }

    @Test
    @DisplayName("Should create error response with all parameters")
    void testConstructor() {
        assertEquals(400, errorResponseDTO.getStatus());
        assertEquals("Bad Request", errorResponseDTO.getError());
        assertEquals("Invalid input", errorResponseDTO.getMessage());
        assertEquals("trace details", errorResponseDTO.getTrace());
    }

    @Test
    @DisplayName("Should set and get status")
    void testSetGetStatus() {
        errorResponseDTO.setStatus(500);
        assertEquals(500, errorResponseDTO.getStatus());
    }

    @Test
    @DisplayName("Should set and get error")
    void testSetGetError() {
        errorResponseDTO.setError("Internal Server Error");
        assertEquals("Internal Server Error", errorResponseDTO.getError());
    }

    @Test
    @DisplayName("Should set and get message")
    void testSetGetMessage() {
        errorResponseDTO.setMessage("Something went wrong");
        assertEquals("Something went wrong", errorResponseDTO.getMessage());
    }

    @Test
    @DisplayName("Should set and get trace")
    void testSetGetTrace() {
        errorResponseDTO.setTrace("new trace");
        assertEquals("new trace", errorResponseDTO.getTrace());
    }

    @Test
    @DisplayName("Should create error response for 404")
    void testNotFoundError() {
        ErrorResponseDTO dto = new ErrorResponseDTO(404, "Not Found", "Resource not found", null);
        assertEquals(404, dto.getStatus());
        assertEquals("Not Found", dto.getError());
    }

    @Test
    @DisplayName("Should create error response for 401")
    void testUnauthorizedError() {
        ErrorResponseDTO dto = new ErrorResponseDTO(401, "Unauthorized", "Invalid credentials", null);
        assertEquals(401, dto.getStatus());
        assertEquals("Unauthorized", dto.getError());
    }
}

