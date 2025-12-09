package sptech.school.v2.cleanarch.domain.enumerated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("AppointmentStatus Enum Tests")
class AppointmentStatusTest {

    @Test
    @DisplayName("Should have SCHEDULED status")
    void testScheduledStatus() {
        AppointmentStatus status = AppointmentStatus.SCHEDULED;
        assertNotNull(status);
        assertEquals("SCHEDULED", status.name());
    }

    @Test
    @DisplayName("Should have COMPLETED status")
    void testCompletedStatus() {
        AppointmentStatus status = AppointmentStatus.COMPLETED;
        assertNotNull(status);
        assertEquals("COMPLETED", status.name());
    }

    @Test
    @DisplayName("Should have CANCELLED status")
    void testCancelledStatus() {
        AppointmentStatus status = AppointmentStatus.CANCELLED;
        assertNotNull(status);
        assertEquals("CANCELLED", status.name());
    }

    @Test
    @DisplayName("Should have exactly 3 statuses")
    void testStatusCount() {
        AppointmentStatus[] statuses = AppointmentStatus.values();
        assertEquals(3, statuses.length);
    }

    @Test
    @DisplayName("Should get status by name")
    void testValueOf() {
        AppointmentStatus status = AppointmentStatus.valueOf("SCHEDULED");
        assertEquals(AppointmentStatus.SCHEDULED, status);
    }
}

