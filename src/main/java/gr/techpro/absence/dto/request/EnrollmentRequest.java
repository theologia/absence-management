package gr.techpro.absence.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EnrollmentRequest {
    @NotNull private Long studentId;
    @NotNull private Long moduleId;
}
