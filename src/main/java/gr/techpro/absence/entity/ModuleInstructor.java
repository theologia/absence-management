package gr.techpro.absence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "module_instructor",
        uniqueConstraints = @UniqueConstraint(columnNames = {"module_id", "instructor_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ModuleInstructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @Enumerated(EnumType.STRING)
    private InstructorRole role;
}
