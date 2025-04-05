package sptech.school.adapters.out.persistence;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import sptech.school.domain.entity.User;

@NoRepositoryBean
public interface JpaUserRepository<T extends User> extends JpaRepository<T, Integer> {
    T findByCpf(String cpf);

    T findByEmailIgnoreCase(String email);

    T findByEmailAndPassword(String email, String password);
}