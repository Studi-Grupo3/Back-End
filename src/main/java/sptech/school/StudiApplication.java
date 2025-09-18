package sptech.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sptech.school.v2.cleanarch.infra.persistence.repository.ContentJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.TeacherJpaRepository;

@SpringBootApplication
@EntityScan(basePackages = {"sptech.school.domain.entity", "sptech.school.v2.cleanarch.domain.entities"})
@EnableJpaRepositories(basePackageClasses = {
        sptech.school.adapters.out.persistence.AppointmentRepository.class,
        TeacherJpaRepository.class,
        ContentJpaRepository.class
})
public class StudiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudiApplication.class, args);
    }

}
