package sptech.school.v2.cleanarch.infra.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;

public interface JpaResourceFileRepository extends JpaRepository<ResourceFile, Long> {
}