package gr.techpro.absence.controller;

import gr.techpro.absence.dto.request.SessionRequest;
import gr.techpro.absence.dto.response.SessionResponse;
import gr.techpro.absence.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/sessions")
@RequiredArgsConstructor
@Tag(name = "Sessions", description = "Session management per module")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @Operation(summary = "Add a session to a module")
    public ResponseEntity<SessionResponse> create(@PathVariable Long moduleId,
                                                   @Valid @RequestBody SessionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(moduleId, req));
    }

    @GetMapping
    @Operation(summary = "List sessions for a module (optional date range filter)")
    public ResponseEntity<List<SessionResponse>> list(
            @PathVariable Long moduleId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(sessionService.getByModule(moduleId, from, to));
    }
}
