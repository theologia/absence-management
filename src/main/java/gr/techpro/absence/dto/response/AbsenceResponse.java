package gr.techpro.absence.dto.response;

import gr.techpro.absence.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AbsenceResponse {
    private Long id;
    private Long enrollmentId;
    private Long sessionId;
    private AttendanceStatus status;
    private String justification;
    private Boolean justified;
    private LocalDateTime recordedAt;
    private LocalDateTime updatedAt;
}
