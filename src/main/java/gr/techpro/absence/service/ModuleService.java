package gr.techpro.absence.service;

import gr.techpro.absence.dto.request.AssignInstructorRequest;
import gr.techpro.absence.dto.request.ModuleRequest;
import gr.techpro.absence.dto.response.ModuleResponse;
import gr.techpro.absence.entity.Module;
import gr.techpro.absence.entity.ModuleInstructor;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.InstructorRepository;
import gr.techpro.absence.repository.ModuleInstructorRepository;
import gr.techpro.absence.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final InstructorRepository instructorRepository;
    private final ModuleInstructorRepository moduleInstructorRepository;

    public ModuleResponse create(ModuleRequest req) {
        Module module = Module.builder()
                .code(req.getCode())
                .title(req.getTitle())
                .credits(req.getCredits())
                .semester(req.getSemester())
                .academicYear(req.getAcademicYear())
                .build();
        return toResponse(moduleRepository.save(module));
    }

    @Transactional(readOnly = true)
    public ModuleResponse getById(Long id) {
        return toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<ModuleResponse> getAll() {
        return moduleRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ModuleResponse update(Long id, ModuleRequest req) {
        Module module = findById(id);
        module.setCode(req.getCode());
        module.setTitle(req.getTitle());
        module.setCredits(req.getCredits());
        module.setSemester(req.getSemester());
        module.setAcademicYear(req.getAcademicYear());
        return toResponse(moduleRepository.save(module));
    }

    public void delete(Long id) {
        moduleRepository.delete(findById(id));
    }

    public void assignInstructor(Long moduleId, AssignInstructorRequest req) {
        Module module = findById(moduleId);
        var instructor = instructorRepository.findById(req.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + req.getInstructorId()));

        moduleInstructorRepository.findByModuleIdAndInstructorId(moduleId, req.getInstructorId())
                .ifPresentOrElse(
                        mi -> mi.setRole(req.getRole()),
                        () -> moduleInstructorRepository.save(ModuleInstructor.builder()
                                .module(module)
                                .instructor(instructor)
                                .role(req.getRole())
                                .build())
                );
    }

    public void removeInstructor(Long moduleId, Long instructorId) {
        ModuleInstructor mi = moduleInstructorRepository.findByModuleIdAndInstructorId(moduleId, instructorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Instructor " + instructorId + " is not assigned to module " + moduleId));
        moduleInstructorRepository.delete(mi);
    }

    public Module findById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + id));
    }

    private ModuleResponse toResponse(Module m) {
        return ModuleResponse.builder()
                .id(m.getId())
                .code(m.getCode())
                .title(m.getTitle())
                .credits(m.getCredits())
                .semester(m.getSemester())
                .academicYear(m.getAcademicYear())
                .createdAt(m.getCreatedAt())
                .build();
    }
}
