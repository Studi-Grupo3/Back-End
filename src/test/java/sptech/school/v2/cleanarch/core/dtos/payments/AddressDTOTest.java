package sptech.school.v2.cleanarch.core.dtos.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("AddressDTO Tests")
class AddressDTOTest {

    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO("Rua Principal", "123", "01234-567");
    }

    @Test
    @DisplayName("Should create address DTO with all fields")
    void testCreateAddressDTO() {
        assertEquals("Rua Principal", addressDTO.streetName());
        assertEquals("123", addressDTO.streetNumber());
        assertEquals("01234-567", addressDTO.zipCode());
    }

    @Test
    @DisplayName("Should create address DTO with different values")
    void testCreateWithDifferentValues() {
        AddressDTO dto = new AddressDTO("Avenida Brasil", "456", "02345-678");
        assertEquals("Avenida Brasil", dto.streetName());
        assertEquals("456", dto.streetNumber());
        assertEquals("02345-678", dto.zipCode());
    }

    @Test
    @DisplayName("Should access all address fields")
    void testAccessFields() {
        assertNotNull(addressDTO.streetName());
        assertNotNull(addressDTO.streetNumber());
        assertNotNull(addressDTO.zipCode());
    }

    @Test
    @DisplayName("Should create multiple different addresses")
    void testMultipleAddresses() {
        AddressDTO addr1 = new AddressDTO("Rua A", "1", "00000-000");
        AddressDTO addr2 = new AddressDTO("Rua B", "2", "11111-111");

        assertEquals("Rua A", addr1.streetName());
        assertEquals("Rua B", addr2.streetName());
        assertEquals("1", addr1.streetNumber());
        assertEquals("2", addr2.streetNumber());
    }
}

