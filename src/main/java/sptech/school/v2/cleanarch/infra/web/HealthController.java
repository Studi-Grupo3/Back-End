package sptech.school.v2.cleanarch.infra.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Value("${app.health.enabled:true}")
    private boolean enabled;

    @Value("${app.version:unknown}")
    private String appVersion;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", enabled ? "UP" : "DISABLED");
        body.put("timestamp", Instant.now().toString());
        body.put("version", appVersion);
        return ResponseEntity.ok(body);
    }
}

