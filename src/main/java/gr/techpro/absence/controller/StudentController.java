package gr.techpro.absence.controller;

import gr.techpro.absence.dto.request.StudentRequest;
import gr.techpro.absence.dto.response.StudentResponse;
import gr.techpro.absence.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Student CRUD operations")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(req));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    @Operation(summary = "List all students")
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentRequest req) {
        return ResponseEntity.ok(studentService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
