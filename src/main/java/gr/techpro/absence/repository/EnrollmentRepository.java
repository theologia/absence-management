package gr.techpro.absence.repository;

import gr.techpro.absence.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentIdAndModuleId(Long studentId, Long moduleId);
    boolean existsByStudentIdAndModuleId(Long studentId, Long moduleId);
    List<Enrollment> findByModuleId(Long moduleId);
    List<Enrollment> findByStudentId(Long studentId);
}
