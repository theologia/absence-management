package gr.techpro.absence.service;

import gr.techpro.absence.dto.request.SessionRequest;
import gr.techpro.absence.dto.response.SessionResponse;
import gr.techpro.absence.entity.Session;
import gr.techpro.absence.exception.AbsenceConflictException;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final ModuleService moduleService;

    public SessionResponse create(Long moduleId, SessionRequest req) {
        if (!req.getEndTime().isAfter(req.getStartTime())) {
            throw new AbsenceConflictException("end_time must be after start_time");
        }
        Session session = Session.builder()
                .module(moduleService.findById(moduleId))
                .sessionDate(req.getSessionDate())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .sessionType(req.getSessionType())
                .topic(req.getTopic())
                .build();
        return toResponse(sessionRepository.save(session));
    }

    @Transactional(readOnly = true)
    public List<SessionResponse> getByModule(Long moduleId, LocalDate from, LocalDate to) {
        moduleService.findById(moduleId);
        List<Session> sessions = (from != null && to != null)
                ? sessionRepository.findByModuleIdAndSessionDateBetween(moduleId, from, to)
                : sessionRepository.findByModuleId(moduleId);
        return sessions.stream().map(this::toResponse).toList();
    }

    public Session findById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found: " + id));
    }

    private SessionResponse toResponse(Session s) {
        return SessionResponse.builder()
                .id(s.getId())
                .moduleId(s.getModule().getId())
                .sessionDate(s.getSessionDate())
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .sessionType(s.getSessionType())
                .topic(s.getTopic())
                .build();
    }
}
