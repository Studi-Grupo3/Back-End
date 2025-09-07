package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;

public interface JpaResourceFileRepository extends JpaRepository<ResourceFile, Long> {
}