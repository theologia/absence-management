package gr.techpro.absence.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleStatsResponse {
    private Long moduleId;
    private String moduleCode;
    private List<SessionAbsenceRate> sessionsWithHighestAbsenceRate;
    private List<StudentAbsenceCount> studentsWithMostAbsences;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SessionAbsenceRate {
        private Long sessionId;
        private LocalDate sessionDate;
        private String topic;
        private long absentCount;
        private long totalEnrolled;
        private double absenceRate;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class StudentAbsenceCount {
        private Long studentId;
        private String studentName;
        private long absentCount;
    }
}
