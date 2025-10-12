package sptech.school.v2.cleanarch.infra.web.queue;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.domain.dto.payments.PaymentRequestDTO;
import sptech.school.v2.cleanarch.core.application.facades.queue.PaymentFacadeContract;

import java.util.Map;

@RestController
@RequestMapping("/v2/payments")
public class PaymentController {

    private final PaymentFacadeContract facade;

    public PaymentController(PaymentFacadeContract facade) {
        this.facade = facade;
    }

    @PostMapping("/async")
    public ResponseEntity<?> requestPayment(@RequestBody PaymentRequestDTO dto) {
        String messageId = facade.requestAsyncPayment(dto);
        return ResponseEntity.accepted().body(Map.of(
                "message", "Pedido de processamento de pagamento aceito",
                "messageId", messageId
        ));
    }
}

