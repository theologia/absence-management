package gr.techpro.absence.repository;

import gr.techpro.absence.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByModuleId(Long moduleId);
    List<Session> findByModuleIdAndSessionDateBetween(Long moduleId, LocalDate from, LocalDate to);
    long countByModuleId(Long moduleId);
}
