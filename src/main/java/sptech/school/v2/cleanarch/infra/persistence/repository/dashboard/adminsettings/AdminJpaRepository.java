package sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.adminsettings;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.Admin;
import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findFirstByOrderByIdAsc();
}
