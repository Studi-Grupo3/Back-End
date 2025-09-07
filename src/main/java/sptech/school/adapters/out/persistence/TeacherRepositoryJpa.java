package sptech.school.adapters.out.persistence;

import jakarta.validation.constraints.Email;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.Optional;

public interface TeacherRepositoryJpa extends JpaUserRepository<Teacher> {
    Optional<Teacher> findByEmail(String email);
    Boolean existsByEmail(@Email String email);
}
