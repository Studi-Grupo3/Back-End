package sptech.school.v2.cleanarch.core.dtos.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("ConfirmPasswordRequestDTO Tests")
class ConfirmPasswordRequestDTOTest {

    private ConfirmPasswordRequestDTO confirmPasswordRequestDTO;

    @BeforeEach
    void setUp() {
        confirmPasswordRequestDTO = new ConfirmPasswordRequestDTO();
    }

    @Test
    @DisplayName("Should create with password parameter")
    void testConstructorWithPassword() {
        ConfirmPasswordRequestDTO dto = new ConfirmPasswordRequestDTO("password123");
        assertEquals("password123", dto.getCurrentPassword());
    }

    @Test
    @DisplayName("Should create empty DTO")
    void testEmptyConstructor() {
        ConfirmPasswordRequestDTO dto = new ConfirmPasswordRequestDTO();
        assertNull(dto.getCurrentPassword());
    }

    @Test
    @DisplayName("Should set and get current password")
    void testSetGetCurrentPassword() {
        confirmPasswordRequestDTO.setCurrentPassword("mypassword");
        assertEquals("mypassword", confirmPasswordRequestDTO.getCurrentPassword());
    }

    @Test
    @DisplayName("Should change password multiple times")
    void testChangePassword() {
        confirmPasswordRequestDTO.setCurrentPassword("first");
        assertEquals("first", confirmPasswordRequestDTO.getCurrentPassword());

        confirmPasswordRequestDTO.setCurrentPassword("second");
        assertEquals("second", confirmPasswordRequestDTO.getCurrentPassword());
    }

    @Test
    @DisplayName("Should set password to null")
    void testSetPasswordNull() {
        confirmPasswordRequestDTO.setCurrentPassword("password");
        confirmPasswordRequestDTO.setCurrentPassword(null);
        assertNull(confirmPasswordRequestDTO.getCurrentPassword());
    }

    @Test
    @DisplayName("Should handle empty string password")
    void testEmptyStringPassword() {
        confirmPasswordRequestDTO.setCurrentPassword("");
        assertEquals("", confirmPasswordRequestDTO.getCurrentPassword());
    }
}

