package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
