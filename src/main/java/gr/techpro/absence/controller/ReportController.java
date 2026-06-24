package gr.techpro.absence.controller;

import gr.techpro.absence.dto.response.AbsenceSummaryResponse;
import gr.techpro.absence.dto.response.ModuleStatsResponse;
import gr.techpro.absence.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Absence reporting and statistics")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/students/{studentId}/modules/{moduleId}")
    @Operation(summary = "Absence summary for a student in a module")
    public ResponseEntity<AbsenceSummaryResponse> studentModuleSummary(
            @PathVariable Long studentId, @PathVariable Long moduleId) {
        return ResponseEntity.ok(reportService.getStudentModuleSummary(studentId, moduleId));
    }

    @GetMapping("/modules/{moduleId}/at-risk")
    @Operation(summary = "Students exceeding the absence threshold in a module")
    public ResponseEntity<List<AbsenceSummaryResponse>> atRisk(
            @PathVariable Long moduleId,
            @RequestParam(required = false) Integer threshold) {
        return ResponseEntity.ok(reportService.getAtRisk(moduleId, threshold));
    }

    @GetMapping("/modules/{moduleId}/stats")
    @Operation(summary = "Module-level absence statistics")
    public ResponseEntity<ModuleStatsResponse> moduleStats(@PathVariable Long moduleId) {
        return ResponseEntity.ok(reportService.getModuleStats(moduleId));
    }
}
