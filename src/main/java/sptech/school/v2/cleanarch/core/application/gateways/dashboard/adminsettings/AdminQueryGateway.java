package sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings;

import sptech.school.v2.cleanarch.domain.entities.Admin;

import java.util.Optional;

public interface AdminQueryGateway {
    Optional<Admin> findFirstAdmin();
}
