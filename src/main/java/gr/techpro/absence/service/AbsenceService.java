package gr.techpro.absence.service;

import gr.techpro.absence.dto.request.AbsenceRequest;
import gr.techpro.absence.dto.request.JustifyRequest;
import gr.techpro.absence.dto.response.AbsenceResponse;
import gr.techpro.absence.entity.Absence;
import gr.techpro.absence.entity.Enrollment;
import gr.techpro.absence.entity.EnrollmentStatus;
import gr.techpro.absence.entity.Session;
import gr.techpro.absence.exception.AbsenceConflictException;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.AbsenceRepository;
import gr.techpro.absence.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionService sessionService;

    public AbsenceResponse record(AbsenceRequest req) {
        Enrollment enrollment = enrollmentRepository.findById(req.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found: " + req.getEnrollmentId()));

        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new AbsenceConflictException("Enrollment " + req.getEnrollmentId() + " is not ACTIVE");
        }

        Session session = sessionService.findById(req.getSessionId());

        if (!session.getModule().getId().equals(enrollment.getModule().getId())) {
            throw new AbsenceConflictException(
                    "Session " + req.getSessionId() + " does not belong to the enrollment's module");
        }

        if (absenceRepository.existsByEnrollmentIdAndSessionId(req.getEnrollmentId(), req.getSessionId())) {
            throw new AbsenceConflictException(
                    "Absence record already exists for enrollment " + req.getEnrollmentId()
                    + " and session " + req.getSessionId());
        }

        Absence absence = Absence.builder()
                .enrollment(enrollment)
                .session(session)
                .status(req.getStatus())
                .justified(false)
                .build();
        return toResponse(absenceRepository.save(absence));
    }

    @Transactional(readOnly = true)
    public AbsenceResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public AbsenceResponse justify(Long id, JustifyRequest req) {
        Absence absence = findById(id);
        absence.setJustified(req.getJustified());
        absence.setJustification(req.getJustification());
        return toResponse(absenceRepository.save(absence));
    }

    @Transactional(readOnly = true)
    public List<AbsenceResponse> query(Long studentId, Long moduleId, Long sessionId) {
        List<Absence> result;
        if (sessionId != null) {
            result = absenceRepository.findBySessionId(sessionId);
        } else if (studentId != null && moduleId != null) {
            result = absenceRepository.findByEnrollmentStudentIdAndEnrollmentModuleId(studentId, moduleId);
        } else if (studentId != null) {
            result = absenceRepository.findByEnrollmentStudentId(studentId);
        } else if (moduleId != null) {
            result = absenceRepository.findByEnrollmentModuleId(moduleId);
        } else {
            result = absenceRepository.findAll();
        }
        return result.stream().map(this::toResponse).toList();
    }

    public Absence findById(Long id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Absence not found: " + id));
    }

    private AbsenceResponse toResponse(Absence a) {
        return AbsenceResponse.builder()
                .id(a.getId())
                .enrollmentId(a.getEnrollment().getId())
                .sessionId(a.getSession().getId())
                .status(a.getStatus())
                .justification(a.getJustification())
                .justified(a.getJustified())
                .recordedAt(a.getRecordedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }
}
