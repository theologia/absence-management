package gr.techpro.absence.dto.response;

import gr.techpro.absence.entity.Semester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleResponse {
    private Long id;
    private String code;
    private String title;
    private Integer credits;
    private Semester semester;
    private Integer academicYear;
    private LocalDateTime createdAt;
}
