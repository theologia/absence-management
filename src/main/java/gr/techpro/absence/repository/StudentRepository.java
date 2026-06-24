package gr.techpro.absence.repository;

import gr.techpro.absence.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    boolean existsByStudentNumber(String studentNumber);
}
