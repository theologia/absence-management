package gr.techpro.absence.service;

import gr.techpro.absence.dto.request.StudentRequest;
import gr.techpro.absence.dto.response.StudentResponse;
import gr.techpro.absence.entity.Student;
import gr.techpro.absence.exception.ResourceNotFoundException;
import gr.techpro.absence.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentResponse create(StudentRequest req) {
        Student student = Student.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .studentNumber(req.getStudentNumber())
                .enrollmentDate(req.getEnrollmentDate())
                .build();
        return toResponse(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        return toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAll() {
        return studentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public StudentResponse update(Long id, StudentRequest req) {
        Student student = findById(id);
        student.setFirstName(req.getFirstName());
        student.setLastName(req.getLastName());
        student.setEmail(req.getEmail());
        student.setStudentNumber(req.getStudentNumber());
        student.setEnrollmentDate(req.getEnrollmentDate());
        return toResponse(studentRepository.save(student));
    }

    public void delete(Long id) {
        studentRepository.delete(findById(id));
    }

    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }

    private StudentResponse toResponse(Student s) {
        return StudentResponse.builder()
                .id(s.getId())
                .firstName(s.getFirstName())
                .lastName(s.getLastName())
                .email(s.getEmail())
                .studentNumber(s.getStudentNumber())
                .enrollmentDate(s.getEnrollmentDate())
                .createdAt(s.getCreatedAt())
                .build();
    }
}
