package gr.techpro.absence.controller;

import gr.techpro.absence.dto.request.AbsenceRequest;
import gr.techpro.absence.dto.request.JustifyRequest;
import gr.techpro.absence.dto.response.AbsenceResponse;
import gr.techpro.absence.service.AbsenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
@RequiredArgsConstructor
@Tag(name = "Absences", description = "Absence recording and justification")
public class AbsenceController {

    private final AbsenceService absenceService;

    @PostMapping
    @Operation(summary = "Record absence / attendance")
    public ResponseEntity<AbsenceResponse> record(@Valid @RequestBody AbsenceRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(absenceService.record(req));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get absence record by ID")
    public ResponseEntity<AbsenceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(absenceService.getById(id));
    }

    @PatchMapping("/{id}/justify")
    @Operation(summary = "Justify or unjustify an absence")
    public ResponseEntity<AbsenceResponse> justify(@PathVariable Long id,
                                                    @Valid @RequestBody JustifyRequest req) {
        return ResponseEntity.ok(absenceService.justify(id, req));
    }

    @GetMapping
    @Operation(summary = "Query absences with optional filters")
    public ResponseEntity<List<AbsenceResponse>> query(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long moduleId,
            @RequestParam(required = false) Long sessionId) {
        return ResponseEntity.ok(absenceService.query(studentId, moduleId, sessionId));
    }
}
