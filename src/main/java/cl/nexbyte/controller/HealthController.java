package cl.nexbyte.controller;

import java.time.OffsetDateTime;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {
  @GetMapping
  public ResponseEntity<Map<String,Object>> get() {
    return ResponseEntity.ok(Map.of(
      "name","nexbyte",
      "version","0.0.1-SNAPSHOT",
      "status","OK",
      "time", OffsetDateTime.now().toString()
    ));
  }
}
