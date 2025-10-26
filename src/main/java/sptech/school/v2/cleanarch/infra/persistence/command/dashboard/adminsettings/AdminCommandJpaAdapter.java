package sptech.school.v2.cleanarch.infra.persistence.command.dashboard.adminsettings;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.domain.entity.Admin;
import sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings.AdminCommandGateway;
import sptech.school.v2.cleanarch.infra.persistence.repository.dashboard.adminsettings.AdminJpaRepository;

@Component
public class AdminCommandJpaAdapter implements AdminCommandGateway {

    private final AdminJpaRepository repository;

    public AdminCommandJpaAdapter(AdminJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Admin save(Admin admin) {
        return repository.save(admin);
    }
}
