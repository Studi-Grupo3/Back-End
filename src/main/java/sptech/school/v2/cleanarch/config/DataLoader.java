package sptech.school.v2.cleanarch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;
import sptech.school.v2.cleanarch.infra.persistence.repository.StudentJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.TeacherJpaRepository;

import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final TeacherJpaRepository teacherRepository;
    private final StudentJpaRepository studentRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataLoader(TeacherJpaRepository teacherRepository, StudentJpaRepository studentRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teacherRepository.count() >= 0) {
            Teacher t1 = new Teacher("Pedro Silva", "pedro.silva@example.com", generateCpf(), bCryptPasswordEncoder.encode("banana"), List.of(Subject.MATHEMATICS, Subject.PHYSICS));
            t1.setHourlyRate(80.0);
            t1.setResumeTeacher("Professor with experience in calculus and mechanics.");
            t1.setYearsExperience("5");
            t1.setAcademicFormation("MSc in Physics");
            teacherRepository.save(t1);

            Teacher t2 = new Teacher("Joao Souza", "joao.souza@example.com", generateCpf(), bCryptPasswordEncoder.encode("banana"), List.of(Subject.ENGLISH));
            t2.setHourlyRate(60.0);
            t2.setResumeTeacher("Native English speaker and literature teacher.");
            t2.setYearsExperience("3");
            t2.setAcademicFormation("BA in English");
            teacherRepository.save(t2);

        }

        if (studentRepository.count() == 0) {
            Student s1 = new Student("Beatriz Costa", "beatriz.costa@example.com", generateCpf(), bCryptPasswordEncoder.encode("banana"));
            s1.setSchoolGrade("9th Grade");
            s1.setSchoolName("Colégio Central");
            studentRepository.save(s1);

            Student s2 = new Student("Diego Alves", "diego.alves@example.com", generateCpf(), bCryptPasswordEncoder.encode("banana"));
            s2.setSchoolGrade("11th Grade");
            s2.setSchoolName("Escola Municipal");
            studentRepository.save(s2);
        }
    }

    // Generates a valid Brazilian CPF string of 11 digits
    private String generateCpf() {
        Random rnd = new Random();
        int[] n = new int[11];
        for (int i = 0; i < 9; i++) {
            n[i] = rnd.nextInt(10);
        }
        // first check digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += n[i] * (10 - i);
        }
        int d1 = 11 - (sum % 11);
        if (d1 >= 10) d1 = 0;
        n[9] = d1;

        // second check digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += n[i] * (11 - i);
        }
        int d2 = 11 - (sum % 11);
        if (d2 >= 10) d2 = 0;
        n[10] = d2;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) sb.append(n[i]);
        return sb.toString();
    }
}
