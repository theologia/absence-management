package gr.techpro.absence.dto.response;

import gr.techpro.absence.entity.SessionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SessionResponse {
    private Long id;
    private Long moduleId;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private SessionType sessionType;
    private String topic;
}
