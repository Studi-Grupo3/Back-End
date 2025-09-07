package sptech.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sptech.school.v2.cleanarch.infra.persistence.repository.TeacherJpaRepository;

@SpringBootApplication
@EntityScan(basePackages = {"sptech.school.domain.entity", "sptech.school.v2.cleanarch.domain.entities"})
public class StudiApplication {
	public static void main(String[] args) {
		SpringApplication.run(StudiApplication.class, args);
	}
}