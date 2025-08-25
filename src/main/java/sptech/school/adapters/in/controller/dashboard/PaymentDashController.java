package sptech.school.adapters.in.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.application.service.dashboard.PaymentDashService;
import sptech.school.domain.dto.response.dashboard.payment.PaymentDashDTO;
import sptech.school.domain.enumerated.PaymentStatus;

@RestController
@RequestMapping("/dashboard/payments")
public class PaymentDashController {

    private final PaymentDashService dashService;

    @Autowired
    public PaymentDashController(PaymentDashService dashService) {
        this.dashService = dashService;
    }

    @GetMapping
    public ResponseEntity<PaymentDashDTO> getDashboard(
            @RequestParam("month") int month,
            @RequestParam("year") int year
    ) {
        PaymentDashDTO dto = dashService.getDashboardData(month, year);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<PaymentStatus> togglePaymentStatus(@PathVariable Integer id) {
        PaymentStatus newStatus = dashService.togglePaymentStatus(id);
        return ResponseEntity.ok(newStatus);
    }
}
