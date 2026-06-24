package gr.techpro.absence.repository;

import gr.techpro.absence.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean existsByEmail(String email);
}
