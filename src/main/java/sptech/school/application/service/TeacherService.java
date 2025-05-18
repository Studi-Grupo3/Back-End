package sptech.school.application.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.application.mappers.TeacherMapper;
import sptech.school.application.usecase.AbstractUserUseCase;
import sptech.school.domain.dto.request.TeacherRequestUpdateDTO;
import sptech.school.domain.entity.Teacher;

import java.util.List;

@Service
public class TeacherService extends AbstractUserUseCase<Teacher, TeacherRequestUpdateDTO> {
    @Autowired
    private TeacherMapper teacherMapper;

    public TeacherService(JpaUserRepository<Teacher> repository, PasswordEncoder passwordEncoder) {
        super(repository, passwordEncoder);
    }

    @Override
    public Teacher validateSpecify(TeacherRequestUpdateDTO dto, Teacher targetUser) {
        teacherMapper.updateTeacherFromDto(dto, targetUser);
        return targetUser;
    }

    public Teacher findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found."));
    }

    public List<Teacher> listAll() {
        return repository.findAll()
                .stream().toList();
    }

    @Transactional
    public void delete(String cpf) {
        if (!repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found.");
        }
        repository.deleteByCpf(cpf);
    }
}