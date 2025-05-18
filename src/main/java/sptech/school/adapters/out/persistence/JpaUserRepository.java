package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sptech.school.domain.entity.User;

import java.util.Optional;

@NoRepositoryBean
public interface JpaUserRepository<T extends User> extends JpaRepository<T, Integer> {
    Optional<T> findByEmail(String email);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
}