package sptech.school.application.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.v2.cleanarch.core.application.mappers.TeacherMapper;
import sptech.school.application.usecase.AbstractUserUseCase;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherUpdateDTO;
import sptech.school.domain.entity.Teacher;

import java.util.List;

@Service
public class TeacherService extends AbstractUserUseCase<Teacher, TeacherUpdateDTO> {
    @Autowired
    private TeacherMapper teacherMapper;

    public TeacherService(JpaUserRepository<Teacher> repository, PasswordEncoder passwordEncoder) {
        super(repository, passwordEncoder);
    }

    @Override
    public Teacher validateSpecify(TeacherUpdateDTO dto,
                                   Teacher targetUser) {
        teacherMapper.updateTeacherFromDto(dto, targetUser);
        return targetUser;
    }

    public List<Teacher> listAll() {
        return repository.findAllByDeletedFalse();
    }

    public Teacher findById(Integer id) {
        return repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Professor não encontrado.")
                );
    }

    @Transactional
    public void delete(Integer id) {
        Teacher teacher = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Professor não encontrado.")
                );
        teacher.setDeleted(true);
        repository.save(teacher);
    }
}