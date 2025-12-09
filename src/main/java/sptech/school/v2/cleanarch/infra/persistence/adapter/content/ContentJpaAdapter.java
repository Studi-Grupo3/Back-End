package sptech.school.v2.cleanarch.infra.persistence.adapter.content;

import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.core.application.gateways.content.ContentGateway;
import sptech.school.v2.cleanarch.domain.entities.Content;
import sptech.school.v2.cleanarch.infra.persistence.repository.ContentJpaRepository;

import java.util.Optional;

@Component
public class ContentJpaAdapter implements ContentGateway {
    private final ContentJpaRepository repository;

    public ContentJpaAdapter(ContentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Content save(Content content) {
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        content.setId(null);
        return repository.save(content);
    }

    @Override
    public Optional<Content> findById(Long id) {
        return repository.findById(id);
    }
}
