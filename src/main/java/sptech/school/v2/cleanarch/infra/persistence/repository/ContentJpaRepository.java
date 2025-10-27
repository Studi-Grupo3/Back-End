package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.v2.cleanarch.domain.entities.Content;

public interface ContentJpaRepository extends JpaRepository<Content, Long> {
}
