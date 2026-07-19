package gr.techpro.absence.dto.response;

import gr.techpro.absence.entity.InstructorRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleInstructorResponse {
    private Long instructorId;
    private String firstName;
    private String lastName;
    private String email;
    private InstructorRole role;
}
