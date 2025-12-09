package sptech.school.v2.cleanarch.core.dtos.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.IdentificationDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("IdentificationDTO Tests")
class IdentificationDTOTest {

    private IdentificationDTO identificationDTO;

    @BeforeEach
    void setUp() {
        identificationDTO = new IdentificationDTO("CPF", "12345678901");
    }

    @Test
    @DisplayName("Should create identification DTO with CPF")
    void testCreateWithCPF() {
        assertEquals("CPF", identificationDTO.type());
        assertEquals("12345678901", identificationDTO.number());
    }

    @Test
    @DisplayName("Should create identification DTO with passport")
    void testCreateWithPassport() {
        IdentificationDTO dto = new IdentificationDTO("PASSPORT", "AB123456");
        assertEquals("PASSPORT", dto.type());
        assertEquals("AB123456", dto.number());
    }

    @Test
    @DisplayName("Should access type and number")
    void testAccessFields() {
        assertNotNull(identificationDTO.type());
        assertNotNull(identificationDTO.number());
    }

    @Test
    @DisplayName("Should create with different types")
    void testDifferentTypes() {
        IdentificationDTO dto1 = new IdentificationDTO("RG", "123456789");
        IdentificationDTO dto2 = new IdentificationDTO("CNH", "987654321");

        assertEquals("RG", dto1.type());
        assertEquals("CNH", dto2.type());
    }
}

