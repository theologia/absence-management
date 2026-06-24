package gr.techpro.absence.service;

import gr.techpro.absence.dto.request.EnrollmentRequest;
import gr.techpro.absence.dto.response.EnrollmentResponse;
import gr.techpro.absence.entity.Enrollment;
import gr.techpro.absence.entity.EnrollmentStatus;
import gr.techpro.absence.exception.DuplicateEnrollmentException;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.EnrollmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final ModuleService moduleService;

    public EnrollmentResponse enroll(EnrollmentRequest req) {
        if (enrollmentRepository.existsByStudentIdAndModuleId(req.getStudentId(), req.getModuleId())) {
            throw new DuplicateEnrollmentException(
                    "Student " + req.getStudentId() + " is already enrolled in module " + req.getModuleId());
        }
        var student = studentService.findById(req.getStudentId());
        var module  = moduleService.findById(req.getModuleId());

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .module(module)
                .enrolledAt(LocalDate.now())
                .status(EnrollmentStatus.ACTIVE)
                .build();
        return toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional(readOnly = true)
    public EnrollmentResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public void drop(Long id) {
        enrollmentRepository.delete(findById(id));
    }

    public Enrollment findById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found: " + id));
    }

    private EnrollmentResponse toResponse(Enrollment e) {
        return EnrollmentResponse.builder()
                .id(e.getId())
                .studentId(e.getStudent().getId())
                .studentName(e.getStudent().getFirstName() + " " + e.getStudent().getLastName())
                .moduleId(e.getModule().getId())
                .moduleCode(e.getModule().getCode())
                .enrolledAt(e.getEnrolledAt())
                .status(e.getStatus())
                .build();
    }
}
