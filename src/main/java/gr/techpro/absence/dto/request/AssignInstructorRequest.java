package gr.techpro.absence.dto.request;

import gr.techpro.absence.entity.InstructorRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AssignInstructorRequest {
    @NotNull private Long instructorId;
    @NotNull private InstructorRole role;
}
