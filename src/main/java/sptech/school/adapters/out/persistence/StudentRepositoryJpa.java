package sptech.school.adapters.out.persistence;

import jakarta.validation.constraints.Email;
import sptech.school.v2.cleanarch.domain.entities.Student;

import java.util.Optional;

public interface StudentRepositoryJpa extends JpaUserRepository<Student> {
    Optional<Student> findByEmail(String email);
    Boolean existsByEmail(@Email String email);
}
