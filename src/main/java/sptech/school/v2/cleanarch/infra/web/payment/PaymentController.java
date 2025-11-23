package sptech.school.v2.cleanarch.infra.web.payment;

import com.mercadopago.resources.payment.Payment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.application.facades.payment.PaymentFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.in.payment.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.internal.payment.payment.PreferenceDTO;

import java.util.Map;

@RestController
public class PaymentController {

    private final PaymentFacadeContract facade;

    public PaymentController(PaymentFacadeContract facade) {
        this.facade = facade;
    }

    @PostMapping("/payments")
    public ResponseEntity<?> processPayment(@Valid @RequestBody PaymentRequestDTO request) {
        try {
            Payment payment = facade.processPayment(request);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            // Retorna 500 com mensagem curta — opcionalmente troque por GlobalExceptionHandler
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao processar pagamento", "message", e.getMessage()));
        }
    }

    @PostMapping("/preferences")
    public ResponseEntity<?> createPreference(@Valid @RequestBody PreferenceDTO dto) {
        try {
            String id = facade.createPreference(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao criar preferência", "message", e.getMessage()));
        }
    }
}
