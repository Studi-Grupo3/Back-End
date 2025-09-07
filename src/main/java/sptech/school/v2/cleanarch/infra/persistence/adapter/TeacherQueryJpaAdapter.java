package sptech.school.v2.cleanarch.infra.persistence.adapter;

import org.springframework.stereotype.Component;
import sptech.school.domain.entity.Teacher;
import sptech.school.v2.cleanarch.core.application.gateways.TeacherQueryGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.TeacherJpaRepository;

import java.util.List;
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
    public List<Teacher> listAll() {
        return teacherJpaRepository.findAll();
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        return teacherJpaRepository.findByEmail(email);
    }
}