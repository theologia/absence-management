package gr.techpro.absence.repository;

import gr.techpro.absence.entity.Absence;
import gr.techpro.absence.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEnrollmentId(Long enrollmentId);
    List<Absence> findBySessionId(Long sessionId);
    Optional<Absence> findByEnrollmentIdAndSessionId(Long enrollmentId, Long sessionId);
    boolean existsByEnrollmentIdAndSessionId(Long enrollmentId, Long sessionId);
    List<Absence> findByEnrollmentStudentId(Long studentId);
    List<Absence> findByEnrollmentModuleId(Long moduleId);
    List<Absence> findByEnrollmentStudentIdAndEnrollmentModuleId(Long studentId, Long moduleId);
    long countByEnrollmentIdAndStatus(Long enrollmentId, AttendanceStatus status);
    long countBySessionIdAndStatus(Long sessionId, AttendanceStatus status);
}
