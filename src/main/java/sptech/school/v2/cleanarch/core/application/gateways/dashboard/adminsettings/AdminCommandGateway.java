package sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings;

import sptech.school.v2.cleanarch.domain.entities.Admin;

public interface AdminCommandGateway {
    Admin save(Admin admin);
}
