package sptech.school.v2.cleanarch.infra.web.queue;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.dtos.out.ContactRequestDTO;
import sptech.school.v2.cleanarch.core.application.facades.queue.EmailFacadeContract;

import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final EmailFacadeContract emailFacade;

    public ContactController(EmailFacadeContract emailFacade) {
        this.emailFacade = emailFacade;
    }

    @PostMapping
    public ResponseEntity<?> enviarContato(@Valid @RequestBody ContactRequestDTO dto) {
        emailFacade.requestContactEmail(dto);
        return ResponseEntity.ok().body(Map.of("message", "Pedido de envio aceito"));
    }
}
