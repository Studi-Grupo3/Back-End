package sptech.school.v2.cleanarch.core.application.gateways.content;

import sptech.school.v2.cleanarch.domain.entities.Content;

import java.util.Optional;

public interface ContentGateway {
    Content save(Content content);
    Optional<Content> findById(Long id);
}
