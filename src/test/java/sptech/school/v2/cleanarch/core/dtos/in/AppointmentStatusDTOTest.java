package sptech.school.v2.cleanarch.core.dtos.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("AppointmentStatusDTO Tests")
class AppointmentStatusDTOTest {

    private AppointmentStatusDTO appointmentStatusDTO;

    @BeforeEach
    void setUp() {
        appointmentStatusDTO = new AppointmentStatusDTO();
    }

    @Test
    @DisplayName("Should set and get status")
    void testSetGetStatus() {
        appointmentStatusDTO.setStatus(AppointmentStatus.SCHEDULED);
        assertEquals(AppointmentStatus.SCHEDULED, appointmentStatusDTO.getStatus());
    }

    @Test
    @DisplayName("Should create with status parameter")
    void testConstructorWithParameter() {
        AppointmentStatusDTO dto = new AppointmentStatusDTO(AppointmentStatus.COMPLETED);
        assertEquals(AppointmentStatus.COMPLETED, dto.getStatus());
    }

    @Test
    @DisplayName("Should initialize with null status")
    void testInitialValue() {
        AppointmentStatusDTO dto = new AppointmentStatusDTO();
        assertNull(dto.getStatus());
    }

    @Test
    @DisplayName("Should change status multiple times")
    void testChangeStatus() {
        appointmentStatusDTO.setStatus(AppointmentStatus.SCHEDULED);
        assertEquals(AppointmentStatus.SCHEDULED, appointmentStatusDTO.getStatus());

        appointmentStatusDTO.setStatus(AppointmentStatus.COMPLETED);
        assertEquals(AppointmentStatus.COMPLETED, appointmentStatusDTO.getStatus());

        appointmentStatusDTO.setStatus(AppointmentStatus.CANCELLED);
        assertEquals(AppointmentStatus.CANCELLED, appointmentStatusDTO.getStatus());
    }
}

