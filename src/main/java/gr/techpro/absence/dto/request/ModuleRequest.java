package gr.techpro.absence.dto.request;

import gr.techpro.absence.entity.Semester;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleRequest {
    @NotBlank private String code;
    @NotBlank private String title;
    @NotNull @Min(1) private Integer credits;
    @NotNull private Semester semester;
    @NotNull private Integer academicYear;
}
