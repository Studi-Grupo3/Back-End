package sptech.school.application.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.application.mappers.StudentMapper;
import sptech.school.application.usecase.AbstractUserUseCase;
import sptech.school.domain.dto.request.StudentRequestDTO;
import sptech.school.domain.dto.request.StudentRequestUpdateDTO;
import sptech.school.domain.dto.request.TeacherRequestDTO;
import sptech.school.domain.dto.response.StudentResponseDTO;
import sptech.school.domain.dto.response.TeacherResponseDTO;
import sptech.school.domain.entity.Student;
import sptech.school.domain.entity.Teacher;

import java.util.List;

@Service
public class StudentService extends AbstractUserUseCase<Student, StudentRequestUpdateDTO> {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentService(JpaUserRepository<Student> repository) {
        super(repository);
    }

    public Student create(@Valid Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        return repository.save(student);
    }


    @Override
    public Student validateSpecify(@Valid StudentRequestUpdateDTO dto, Student targetUser) {
        studentMapper.updateStudentFromDto(dto, targetUser);
        return targetUser;
    }

    public Student findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found."));
    }

    public List<StudentResponseDTO> listAll() {
        return repository.findAll()
                .stream().map(studentMapper::toDtoResponse).toList();
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found.");
        }
        repository.deleteById(id);
    }
}