package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.v2.cleanarch.domain.entities.Content;

public interface JpaContentRepository extends JpaRepository<Content, Long> {
}
