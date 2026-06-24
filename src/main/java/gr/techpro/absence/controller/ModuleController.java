package gr.techpro.absence.controller;

import gr.techpro.absence.dto.request.AssignInstructorRequest;
import gr.techpro.absence.dto.request.ModuleRequest;
import gr.techpro.absence.dto.response.ModuleResponse;
import gr.techpro.absence.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Tag(name = "Modules", description = "Module CRUD and instructor assignment")
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping
    @Operation(summary = "Create a module")
    public ResponseEntity<ModuleResponse> create(@Valid @RequestBody ModuleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.create(req));
    }

    @GetMapping
    @Operation(summary = "List all modules")
    public ResponseEntity<List<ModuleResponse>> getAll() {
        return ResponseEntity.ok(moduleService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get module by ID")
    public ResponseEntity<ModuleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update module")
    public ResponseEntity<ModuleResponse> update(@PathVariable Long id, @Valid @RequestBody ModuleRequest req) {
        return ResponseEntity.ok(moduleService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete module")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        moduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/instructors")
    @Operation(summary = "Assign instructor to module")
    public ResponseEntity<Void> assignInstructor(@PathVariable Long id,
                                                  @Valid @RequestBody AssignInstructorRequest req) {
        moduleService.assignInstructor(id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/instructors/{instructorId}")
    @Operation(summary = "Remove instructor from module")
    public ResponseEntity<Void> removeInstructor(@PathVariable Long id, @PathVariable Long instructorId) {
        moduleService.removeInstructor(id, instructorId);
        return ResponseEntity.noContent().build();
    }
}
