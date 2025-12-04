package sptech.school.v2.cleanarch.core.dtos.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.AddressDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.IdentificationDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.PayerDTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("PaymentRequestDTO Tests")
class PaymentRequestDTOTest {

    private PaymentRequestDTO paymentRequestDTO;
    private AddressDTO addressDTO;
    private IdentificationDTO identificationDTO;
    private PayerDTO payerDTO;

    @BeforeEach
    void setUp() {
        addressDTO = new AddressDTO("Rua Principal", "123", "01234-567");
        identificationDTO = new IdentificationDTO("CPF", "12345678901");
        payerDTO = new PayerDTO("user@email.com", "João Silva", identificationDTO, addressDTO);

        paymentRequestDTO = new PaymentRequestDTO(
                BigDecimal.valueOf(100.0),
                "token123",
                "Lesson payment",
                1,
                "payment_method_1",
                payerDTO
        );
    }

    @Test
    @DisplayName("Should create payment request DTO")
    void testCreatePaymentRequestDTO() {
        assertEquals(BigDecimal.valueOf(100.0), paymentRequestDTO.transactionAmount());
        assertEquals("token123", paymentRequestDTO.token());
        assertEquals("Lesson payment", paymentRequestDTO.description());
        assertEquals(1, paymentRequestDTO.installments());
        assertEquals("payment_method_1", paymentRequestDTO.paymentMethodId());
        assertNotNull(paymentRequestDTO.payer());
    }

    @Test
    @DisplayName("Should create payment request with multiple installments")
    void testPaymentWithMultipleInstallments() {
        PaymentRequestDTO dto = new PaymentRequestDTO(
                BigDecimal.valueOf(300.0),
                "token456",
                "Package payment",
                3,
                "payment_method_2",
                payerDTO
        );

        assertEquals(3, dto.installments());
        assertEquals(BigDecimal.valueOf(300.0), dto.transactionAmount());
    }

    @Test
    @DisplayName("Should access all payment fields")
    void testAccessPaymentFields() {
        assertNotNull(paymentRequestDTO.transactionAmount());
        assertNotNull(paymentRequestDTO.token());
        assertNotNull(paymentRequestDTO.description());
        assertNotNull(paymentRequestDTO.installments());
        assertNotNull(paymentRequestDTO.paymentMethodId());
        assertNotNull(paymentRequestDTO.payer());
    }

    @Test
    @DisplayName("Should create payment with different amounts")
    void testDifferentPaymentAmounts() {
        PaymentRequestDTO payment1 = new PaymentRequestDTO(
                BigDecimal.valueOf(50.0),
                "token1",
                "desc1",
                1,
                "method1",
                payerDTO
        );

        PaymentRequestDTO payment2 = new PaymentRequestDTO(
                BigDecimal.valueOf(500.0),
                "token2",
                "desc2",
                5,
                "method2",
                payerDTO
        );

        assertEquals(BigDecimal.valueOf(50.0), payment1.transactionAmount());
        assertEquals(BigDecimal.valueOf(500.0), payment2.transactionAmount());
    }
}

