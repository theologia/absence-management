package gr.techpro.absence.dto.request;

import gr.techpro.absence.entity.SessionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SessionRequest {
    @NotNull private LocalDate sessionDate;
    @NotNull private LocalTime startTime;
    @NotNull private LocalTime endTime;
    @NotNull private SessionType sessionType;
    private String topic;
}
