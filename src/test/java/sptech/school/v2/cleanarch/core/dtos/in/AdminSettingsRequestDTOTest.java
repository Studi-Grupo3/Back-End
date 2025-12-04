package sptech.school.v2.cleanarch.core.dtos.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("AdminSettingsRequestDTO Tests")
class AdminSettingsRequestDTOTest {

    private AdminSettingsRequestDTO adminSettingsRequestDTO;

    @BeforeEach
    void setUp() {
        adminSettingsRequestDTO = new AdminSettingsRequestDTO();
    }

    @Test
    @DisplayName("Should set and get email")
    void testSetGetEmail() {
        adminSettingsRequestDTO.setEmail("admin@email.com");
        assertEquals("admin@email.com", adminSettingsRequestDTO.getEmail());
    }

    @Test
    @DisplayName("Should set and get current password")
    void testSetGetCurrentPassword() {
        adminSettingsRequestDTO.setCurrentPassword("currentPass123");
        assertEquals("currentPass123", adminSettingsRequestDTO.getCurrentPassword());
    }

    @Test
    @DisplayName("Should set and get new password")
    void testSetGetNewPassword() {
        adminSettingsRequestDTO.setNewPassword("newPass456");
        assertEquals("newPass456", adminSettingsRequestDTO.getNewPassword());
    }

    @Test
    @DisplayName("Should set and get notify payments")
    void testSetGetNotifyPayments() {
        adminSettingsRequestDTO.setNotifyPayments(true);
        assertEquals(true, adminSettingsRequestDTO.getNotifyPayments());
    }

    @Test
    @DisplayName("Should set and get notify appointments")
    void testSetGetNotifyAppointments() {
        adminSettingsRequestDTO.setNotifyAppointments(false);
        assertEquals(false, adminSettingsRequestDTO.getNotifyAppointments());
    }

    @Test
    @DisplayName("Should set and get notify cancellations")
    void testSetGetNotifyCancellations() {
        adminSettingsRequestDTO.setNotifyCancellations(true);
        assertEquals(true, adminSettingsRequestDTO.getNotifyCancellations());
    }

    @Test
    @DisplayName("Should set all fields")
    void testSetAllFields() {
        adminSettingsRequestDTO.setEmail("admin@email.com");
        adminSettingsRequestDTO.setCurrentPassword("current");
        adminSettingsRequestDTO.setNewPassword("new");
        adminSettingsRequestDTO.setNotifyPayments(true);
        adminSettingsRequestDTO.setNotifyAppointments(true);
        adminSettingsRequestDTO.setNotifyCancellations(false);

        assertEquals("admin@email.com", adminSettingsRequestDTO.getEmail());
        assertEquals("current", adminSettingsRequestDTO.getCurrentPassword());
        assertEquals("new", adminSettingsRequestDTO.getNewPassword());
        assertEquals(true, adminSettingsRequestDTO.getNotifyPayments());
        assertEquals(true, adminSettingsRequestDTO.getNotifyAppointments());
        assertEquals(false, adminSettingsRequestDTO.getNotifyCancellations());
    }

    @Test
    @DisplayName("Should initialize with null values")
    void testInitialValues() {
        AdminSettingsRequestDTO dto = new AdminSettingsRequestDTO();
        assertNull(dto.getEmail());
        assertNull(dto.getCurrentPassword());
        assertNull(dto.getNewPassword());
        assertNull(dto.getNotifyPayments());
        assertNull(dto.getNotifyAppointments());
        assertNull(dto.getNotifyCancellations());
    }
}

