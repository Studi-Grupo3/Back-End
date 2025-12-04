package sptech.school.v2.cleanarch.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Responsible Entity Tests")
class ResponsibleTest {

    private Responsible responsible;

    @BeforeEach
    void setUp() {
        responsible = new Responsible();
    }

    @Test
    @DisplayName("Should set and get responsible name")
    void testSetGetResponsibleName() {
        responsible.setResponsibleName("Maria Silva");
        assertEquals("Maria Silva", responsible.getResponsibleName());
    }

    @Test
    @DisplayName("Should set and get kinship")
    void testSetGetKinship() {
        responsible.setKinship("Mãe");
        assertEquals("Mãe", responsible.getKinship());
    }

    @Test
    @DisplayName("Should set and get responsible CPF")
    void testSetGetResponsibleCpf() {
        responsible.setResponsibleCpf("98765432101");
        assertEquals("98765432101", responsible.getResponsibleCpf());
    }

    @Test
    @DisplayName("Should set and get responsible cellphone number")
    void testSetGetResponsibleCellphoneNumber() {
        responsible.setResponsibleCellphoneNumber("11988776655");
        assertEquals("11988776655", responsible.getResponsibleCellphoneNumber());
    }

    @Test
    @DisplayName("Should set and get responsible email")
    void testSetGetResponsibleEmail() {
        responsible.setResponsibleEmail("maria@email.com");
        assertEquals("maria@email.com", responsible.getResponsibleEmail());
    }

    @Test
    @DisplayName("Should create empty responsible with no args constructor")
    void testEmptyConstructor() {
        Responsible emptyResponsible = new Responsible();
        assertNull(emptyResponsible.getResponsibleName());
        assertNull(emptyResponsible.getKinship());
    }

    @Test
    @DisplayName("Should set all fields correctly")
    void testSetAllFields() {
        responsible.setResponsibleName("João Silva");
        responsible.setKinship("Pai");
        responsible.setResponsibleCpf("12345678901");
        responsible.setResponsibleCellphoneNumber("11987654321");
        responsible.setResponsibleEmail("joao@email.com");

        assertEquals("João Silva", responsible.getResponsibleName());
        assertEquals("Pai", responsible.getKinship());
        assertEquals("12345678901", responsible.getResponsibleCpf());
        assertEquals("11987654321", responsible.getResponsibleCellphoneNumber());
        assertEquals("joao@email.com", responsible.getResponsibleEmail());
    }
}

