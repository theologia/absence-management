package gr.techpro.absence.service;

import gr.techpro.absence.dto.request.InstructorRequest;
import gr.techpro.absence.dto.response.InstructorResponse;
import gr.techpro.absence.entity.Instructor;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorResponse create(InstructorRequest req) {
        Instructor instructor = Instructor.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .build();
        return toResponse(instructorRepository.save(instructor));
    }

    @Transactional(readOnly = true)
    public InstructorResponse getById(Long id) {
        return toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<InstructorResponse> getAll() {
        return instructorRepository.findAll().stream().map(this::toResponse).toList();
    }

    public InstructorResponse update(Long id, InstructorRequest req) {
        Instructor instructor = findById(id);
        instructor.setFirstName(req.getFirstName());
        instructor.setLastName(req.getLastName());
        instructor.setEmail(req.getEmail());
        return toResponse(instructorRepository.save(instructor));
    }

    public void delete(Long id) {
        instructorRepository.delete(findById(id));
    }

    public Instructor findById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found: " + id));
    }

    private InstructorResponse toResponse(Instructor i) {
        return InstructorResponse.builder()
                .id(i.getId())
                .firstName(i.getFirstName())
                .lastName(i.getLastName())
                .email(i.getEmail())
                .createdAt(i.getCreatedAt())
                .build();
    }
}
