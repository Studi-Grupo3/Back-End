package sptech.school.application.usecase;

import sptech.school.v2.cleanarch.domain.entities.Content;

import java.util.Optional;

public interface ContentRepositoryUseCase {
    Content save(Content content);
    Optional<Content> findById(Long id);
}
