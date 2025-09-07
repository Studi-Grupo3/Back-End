package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sptech.school.v2.cleanarch.domain.entities.User;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface JpaUserRepository<T extends User> extends JpaRepository<T, Integer> {

    List<T> findAllByDeletedFalse();

    Optional<T> findByIdAndDeletedFalse(Integer id);

    Optional<T> findByEmail(String email);
}