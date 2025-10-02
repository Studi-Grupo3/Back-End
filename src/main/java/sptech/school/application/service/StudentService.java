package sptech.school.application.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.v2.cleanarch.core.application.mappers.StudentMapper;
import sptech.school.application.usecase.AbstractUserUseCase;
import sptech.school.v2.cleanarch.core.dtos.in.student.StudentUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.out.student.StudentResponseDTO;
import sptech.school.domain.entity.Student;

import java.util.List;

@Service
public class StudentService extends AbstractUserUseCase<Student, StudentUpdateDTO> {
    @Autowired
    private StudentMapper studentMapper;

    public StudentService(JpaUserRepository<Student> repository, PasswordEncoder passwordEncoder) {
        super(repository, passwordEncoder);
    }

    @Override
    public Student validateSpecify(@Valid StudentUpdateDTO dto, Student targetUser) {
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