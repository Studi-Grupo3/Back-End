package sptech.school.v2.cleanarch.domain.enumerated;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("PaymentStatus Enum Tests")
class PaymentStatusTest {

    @Test
    @DisplayName("Should have PENDING status")
    void testPendingStatus() {
        PaymentStatus status = PaymentStatus.PENDING;
        assertNotNull(status);
        assertEquals("No payment has been made", status.getDescription());
    }

    @Test
    @DisplayName("Should have PARTIAL status")
    void testPartialStatus() {
        PaymentStatus status = PaymentStatus.PARTIAL;
        assertNotNull(status);
        assertEquals("50% payment made", status.getDescription());
    }

    @Test
    @DisplayName("Should have PAID status")
    void testPaidStatus() {
        PaymentStatus status = PaymentStatus.PAID;
        assertNotNull(status);
        assertEquals("Full payment made", status.getDescription());
    }

    @Test
    @DisplayName("Should have CANCELLED status")
    void testCancelledStatus() {
        PaymentStatus status = PaymentStatus.CANCELLED;
        assertNotNull(status);
        assertEquals("Payment cancelled", status.getDescription());
    }

    @Test
    @DisplayName("Should have exactly 4 statuses")
    void testStatusCount() {
        PaymentStatus[] statuses = PaymentStatus.values();
        assertEquals(4, statuses.length);
    }

    @Test
    @DisplayName("Should get status by name")
    void testValueOf() {
        PaymentStatus status = PaymentStatus.valueOf("PENDING");
        assertEquals(PaymentStatus.PENDING, status);
    }

    @Test
    @DisplayName("Should return correct description for PENDING")
    void testPendingDescription() {
        assertEquals("No payment has been made", PaymentStatus.PENDING.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for PAID")
    void testPaidDescription() {
        assertEquals("Full payment made", PaymentStatus.PAID.getDescription());
    }
}

