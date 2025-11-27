package sptech.school.v2.cleanarch.infra.persistence.query.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.core.application.gateways.teacher.TeacherQueryGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;

import java.util.Optional;

@Component
public class TeacherQueryJpaAdapter implements TeacherQueryGateway {

    private final TeacherJpaRepository teacherJpaRepository;

    public TeacherQueryJpaAdapter(TeacherJpaRepository teacherJpaRepository) {
        this.teacherJpaRepository = teacherJpaRepository;
    }

    @Override
    public boolean teacherExistsByEmail(String email) {
        return teacherJpaRepository.existsByEmail(email);
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherJpaRepository.findById(id).orElse(null);
    }

    @Override
    public boolean teacherExistsByCpf(String cpf) {
        return teacherJpaRepository.existsByCpf(cpf);
    }

    @Override
    public Page<Teacher> listAll(Pageable pageable) {
        return teacherJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        return teacherJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<Integer> findIdByEmail(String email) {
        return teacherJpaRepository.findIdByEmail(email);
    }

    @Override
    public Optional<Integer> findByCpf(String cpf) {
        return teacherJpaRepository.findIdByCpf(cpf);
    }
}