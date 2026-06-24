package gr.techpro.absence.repository;

import gr.techpro.absence.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    boolean existsByCode(String code);
}
