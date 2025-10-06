package sptech.school.v2.cleanarch.core.application.gateways.dashboard.adminsettings;

import sptech.school.domain.entity.Admin;

public interface AdminCommandGateway {
    Admin save(Admin admin);
}
