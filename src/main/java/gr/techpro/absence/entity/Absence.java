package gr.techpro.absence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "absence",
        uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_id", "session_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

    private String justification;
    private Boolean justified;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime recordedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
