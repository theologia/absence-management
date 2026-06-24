package gr.techpro.absence.controller;

import gr.techpro.absence.dto.request.InstructorRequest;
import gr.techpro.absence.dto.response.InstructorResponse;
import gr.techpro.absence.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructors", description = "Instructor CRUD operations")
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping
    @Operation(summary = "Create a new instructor")
    public ResponseEntity<InstructorResponse> create(@Valid @RequestBody InstructorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.create(req));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get instructor by ID")
    public ResponseEntity<InstructorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getById(id));
    }

    @GetMapping
    @Operation(summary = "List all instructors")
    public ResponseEntity<List<InstructorResponse>> getAll() {
        return ResponseEntity.ok(instructorService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update instructor")
    public ResponseEntity<InstructorResponse> update(@PathVariable Long id, @Valid @RequestBody InstructorRequest req) {
        return ResponseEntity.ok(instructorService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete instructor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instructorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
