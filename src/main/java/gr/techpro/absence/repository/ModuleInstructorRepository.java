package gr.techpro.absence.repository;

import gr.techpro.absence.entity.ModuleInstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleInstructorRepository extends JpaRepository<ModuleInstructor, Long> {
    List<ModuleInstructor> findByModuleId(Long moduleId);
    Optional<ModuleInstructor> findByModuleIdAndInstructorId(Long moduleId, Long instructorId);
    boolean existsByModuleIdAndInstructorId(Long moduleId, Long instructorId);
}
