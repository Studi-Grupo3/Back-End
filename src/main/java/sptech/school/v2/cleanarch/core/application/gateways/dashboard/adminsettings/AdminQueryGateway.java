package sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings;

import sptech.school.domain.entity.Admin;

import java.util.Optional;

public interface AdminQueryGateway {
    Optional<Admin> findFirstAdmin();
}
