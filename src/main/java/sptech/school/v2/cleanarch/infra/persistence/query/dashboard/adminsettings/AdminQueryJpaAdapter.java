package sptech.school.v2.cleanarch.infra.persistence.query.dashboard.adminsettings;

import org.springframework.stereotype.Component;
import sptech.school.domain.entity.Admin;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings.AdminQueryGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.adminsettings.AdminJpaRepository;

import java.util.Optional;

@Component
public class AdminQueryJpaAdapter implements AdminQueryGateway {

    private final AdminJpaRepository repository;

    public AdminQueryJpaAdapter(AdminJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Admin> findFirstAdmin() {
        return repository.findFirstByOrderByIdAsc();
    }
}
