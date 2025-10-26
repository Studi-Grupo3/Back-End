package sptech.school.v2.cleanarch.infra.web.dashboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.core.application.facades.dashboard.payment.PaymentDashFacadeContract;
import sptech.school.v2.cleanarch.core.dtos.out.dashboard.payment.PaymentDashResponseDTO;

@RestController
@RequestMapping("/dashboard/payments")
public class PaymentDashController {

    private final PaymentDashFacadeContract facade;

    public PaymentDashController(PaymentDashFacadeContract facade) {
        this.facade = facade;
    }

    @GetMapping
    @Operation(summary = "Retorna dados do dashboard de pagamentos", description = "Retorna as métricas e dados do dashboard de pagamentos para o mês e ano informados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do dashboard retornados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public ResponseEntity<PaymentDashResponseDTO> getDashboard(
            @Parameter(description = "Mês (1-12) para consulta", required = true) @RequestParam("month") int month,
            @Parameter(description = "Ano (ex: 2025) para consulta", required = true) @RequestParam("year") int year
    ) {
        PaymentDashResponseDTO dto = facade.getPaymentDashData();
        return ResponseEntity.ok(dto);
    }

//    @PostMapping("/{id}/toggle")
//    @Operation(summary = "Alterna o status de um pagamento", description = "Alterna o status do pagamento identificado por id (ex.: PENDING -> PAID). Retorna o novo status.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Status do pagamento alternado com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
//            @ApiResponse(responseCode = "400", description = "Requisição inválida")
//    })
//    public ResponseEntity<PaymentStatus> togglePaymentStatus(
//            @Parameter(description = "ID do pagamento a ser alternado", required = true) @PathVariable Integer id
//    ) {
//        PaymentStatus newStatus = facade.togglePaymentStatus(id);
//        return ResponseEntity.ok(newStatus);
//    }
}
