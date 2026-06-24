package gr.techpro.absence.dto.response;

import gr.techpro.absence.entity.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EnrollmentResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long moduleId;
    private String moduleCode;
    private LocalDate enrolledAt;
    private EnrollmentStatus status;
}
