package gr.techpro.absence.controller;

import gr.techpro.absence.dto.request.EnrollmentRequest;
import gr.techpro.absence.dto.response.EnrollmentResponse;
import gr.techpro.absence.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Enroll students in modules")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Enroll student in module")
    public ResponseEntity<EnrollmentResponse> enroll(@Valid @RequestBody EnrollmentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enroll(req));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by ID")
    public ResponseEntity<EnrollmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Drop an enrollment")
    public ResponseEntity<Void> drop(@PathVariable Long id) {
        enrollmentService.drop(id);
        return ResponseEntity.noContent().build();
    }
}
