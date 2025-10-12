//package sptech.school.adapters.in.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sptech.school.application.service.PaymentService;
//import sptech.school.application.service.PreferenceService;
//import sptech.school.domain.dto.payments.PaymentRequestDTO;
//import sptech.school.domain.dto.payments.PreferenceDTO;
//
//import java.util.Collections;
//
//@CrossOrigin(origins = "http://localhost:5173")
//@RestController
//@RequestMapping("/payments")
//public class PaymentController {
//    private final PaymentService paymentService;
//    private final PreferenceService preferenceService;
//
//    public PaymentController(PaymentService paymentService, PreferenceService preferenceService) {
//        this.paymentService = paymentService;
//        this.preferenceService = preferenceService;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO request) {
//        try {
//            return ResponseEntity.ok(paymentService.processPayment(request));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Erro ao processar pagamento: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/preference")
//    public ResponseEntity<?> createPreference(@RequestBody PreferenceDTO preference) {
//        try {
//            String preferenceId = preferenceService.createPreference(preference.amount(), preference.payer_email());
//            return ResponseEntity.ok(Collections.singletonMap("preferenceId", preferenceId));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a preferência");
//        }
//    }
//}