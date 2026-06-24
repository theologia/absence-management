package gr.techpro.absence.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AbsenceSummaryResponse {
    private Long studentId;
    private String studentName;
    private Long moduleId;
    private String moduleCode;
    private long totalSessions;
    private long attended;
    private long absent;
    private long justifiedAbsences;
    private double absenceRate;
}
