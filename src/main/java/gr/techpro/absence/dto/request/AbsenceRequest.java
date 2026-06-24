package gr.techpro.absence.dto.request;

import gr.techpro.absence.entity.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AbsenceRequest {
    @NotNull private Long enrollmentId;
    @NotNull private Long sessionId;
    @NotNull private AttendanceStatus status;
}
