package gr.techpro.absence.service;

import gr.techpro.absence.dto.response.AbsenceSummaryResponse;
import gr.techpro.absence.dto.response.ModuleStatsResponse;
import gr.techpro.absence.entity.Absence;
import gr.techpro.absence.entity.AttendanceStatus;
import gr.techpro.absence.entity.Enrollment;
import gr.techpro.absence.entity.EnrollmentStatus;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.AbsenceRepository;
import gr.techpro.absence.repository.EnrollmentRepository;
import gr.techpro.absence.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final AbsenceRepository absenceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final SessionRepository sessionRepository;
    private final ModuleService moduleService;

    @Value("${absence.threshold:33}")
    private int defaultThreshold;

    public AbsenceSummaryResponse getStudentModuleSummary(Long studentId, Long moduleId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndModuleId(studentId, moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No enrollment found for student " + studentId + " in module " + moduleId));

        long totalSessions = sessionRepository.countByModuleId(moduleId);
        List<Absence> absences = absenceRepository.findByEnrollmentId(enrollment.getId());

        long absentCount    = absences.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long attendedCount  = absences.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT
                                                         || a.getStatus() == AttendanceStatus.LATE).count();
        long justifiedCount = absences.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT && Boolean.TRUE.equals(a.getJustified()))
                .count();

        double absenceRate = totalSessions > 0 ? (double) absentCount / totalSessions * 100.0 : 0.0;

        return AbsenceSummaryResponse.builder()
                .studentId(studentId)
                .studentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName())
                .moduleId(moduleId)
                .moduleCode(enrollment.getModule().getCode())
                .totalSessions(totalSessions)
                .attended(attendedCount)
                .absent(absentCount)
                .justifiedAbsences(justifiedCount)
                .absenceRate(absenceRate)
                .build();
    }

    public List<AbsenceSummaryResponse> getAtRisk(Long moduleId, Integer threshold) {
        moduleService.findById(moduleId);
        int effectiveThreshold = (threshold != null) ? threshold : defaultThreshold;
        long totalSessions = sessionRepository.countByModuleId(moduleId);
        if (totalSessions == 0) return List.of();

        return enrollmentRepository.findByModuleId(moduleId).stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .map(e -> buildSummary(e, moduleId, totalSessions))
                .filter(s -> s.getAbsenceRate() > effectiveThreshold)
                .toList();
    }

    public ModuleStatsResponse getModuleStats(Long moduleId) {
        var module = moduleService.findById(moduleId);

        List<Enrollment> activeEnrollments = enrollmentRepository.findByModuleId(moduleId).stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.ACTIVE)
                .toList();
        long totalEnrolled = activeEnrollments.size();

        List<ModuleStatsResponse.SessionAbsenceRate> sessionRates = sessionRepository.findByModuleId(moduleId)
                .stream()
                .map(session -> {
                    long absentCount = absenceRepository.countBySessionIdAndStatus(
                            session.getId(), AttendanceStatus.ABSENT);
                    double rate = totalEnrolled > 0 ? (double) absentCount / totalEnrolled * 100.0 : 0.0;
                    return ModuleStatsResponse.SessionAbsenceRate.builder()
                            .sessionId(session.getId())
                            .sessionDate(session.getSessionDate())
                            .topic(session.getTopic())
                            .absentCount(absentCount)
                            .totalEnrolled(totalEnrolled)
                            .absenceRate(rate)
                            .build();
                })
                .sorted(Comparator.comparingDouble(ModuleStatsResponse.SessionAbsenceRate::getAbsenceRate).reversed())
                .toList();

        List<ModuleStatsResponse.StudentAbsenceCount> studentCounts = activeEnrollments.stream()
                .map(e -> {
                    long absentCount = absenceRepository.countByEnrollmentIdAndStatus(
                            e.getId(), AttendanceStatus.ABSENT);
                    return ModuleStatsResponse.StudentAbsenceCount.builder()
                            .studentId(e.getStudent().getId())
                            .studentName(e.getStudent().getFirstName() + " " + e.getStudent().getLastName())
                            .absentCount(absentCount)
                            .build();
                })
                .sorted(Comparator.comparingLong(ModuleStatsResponse.StudentAbsenceCount::getAbsentCount).reversed())
                .toList();

        return ModuleStatsResponse.builder()
                .moduleId(moduleId)
                .moduleCode(module.getCode())
                .sessionsWithHighestAbsenceRate(sessionRates)
                .studentsWithMostAbsences(studentCounts)
                .build();
    }

    private AbsenceSummaryResponse buildSummary(Enrollment e, Long moduleId, long totalSessions) {
        List<Absence> absences = absenceRepository.findByEnrollmentId(e.getId());
        long absentCount    = absences.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
        long attendedCount  = absences.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT
                                                         || a.getStatus() == AttendanceStatus.LATE).count();
        long justifiedCount = absences.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT && Boolean.TRUE.equals(a.getJustified()))
                .count();
        double absenceRate = (double) absentCount / totalSessions * 100.0;

        return AbsenceSummaryResponse.builder()
                .studentId(e.getStudent().getId())
                .studentName(e.getStudent().getFirstName() + " " + e.getStudent().getLastName())
                .moduleId(moduleId)
                .moduleCode(e.getModule().getCode())
                .totalSessions(totalSessions)
                .attended(attendedCount)
                .absent(absentCount)
                .justifiedAbsences(justifiedCount)
                .absenceRate(absenceRate)
                .build();
    }
}
