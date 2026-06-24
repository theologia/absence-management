package gr.techpro.absence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "module")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String title;
    private Integer credits;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    private Integer academicYear;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
