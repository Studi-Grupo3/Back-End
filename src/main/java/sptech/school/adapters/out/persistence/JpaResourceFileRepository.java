package sptech.school.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.domain.entity.ResourceFile;

public interface JpaResourceFileRepository extends JpaRepository<ResourceFile, Integer> {
}