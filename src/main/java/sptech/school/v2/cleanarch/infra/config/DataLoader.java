package sptech.school.v2.cleanarch.infra.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sptech.school.adapters.out.persistence.StudentRepositoryJpa;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final TeacherRepositoryJpa teacherRepository;
    private final StudentRepositoryJpa studentRepository;

    public DataLoader(TeacherRepositoryJpa teacherRepository, StudentRepositoryJpa studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teacherRepository.count() == 0) {
            Teacher t1 = new Teacher("Ana Silva", "ana.silva@example.com", "11122233344", "password", List.of(Subject.MATHEMATICS, Subject.PHYSICS));
            t1.setHourlyRate(80.0);
            t1.setResumeTeacher("Professor with experience in calculus and mechanics.");
            t1.setYearsExperience("5");
            t1.setAcademicFormation("MSc in Physics");
            teacherRepository.save(t1);

            Teacher t2 = new Teacher("Carlos Souza", "carlos.souza@example.com", "22233344455", "password", List.of(Subject.ENGLISH));
            t2.setHourlyRate(60.0);
            t2.setResumeTeacher("Native English speaker and literature teacher.");
            t2.setYearsExperience("3");
            t2.setAcademicFormation("BA in English");
            teacherRepository.save(t2);
        }

        if (studentRepository.count() == 0) {
            Student s1 = new Student("Beatriz Costa", "beatriz.costa@example.com", "33344455566", "password");
            s1.setSchoolGrade("9th Grade");
            s1.setSchoolName("Colégio Central");
            studentRepository.save(s1);

            Student s2 = new Student("Diego Alves", "diego.alves@example.com", "44455566677", "password");
            s2.setSchoolGrade("11th Grade");
            s2.setSchoolName("Escola Municipal");
            studentRepository.save(s2);
        }
    }
}

