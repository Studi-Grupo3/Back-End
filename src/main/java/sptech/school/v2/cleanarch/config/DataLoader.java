package sptech.school.v2.cleanarch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sptech.school.v2.cleanarch.domain.Responsible;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.entities.Appointment;
import sptech.school.v2.cleanarch.domain.enumerated.AppointmentStatus;
import sptech.school.v2.cleanarch.domain.enumerated.PaymentStatus;
import sptech.school.v2.cleanarch.domain.enumerated.Subject;
import sptech.school.v2.cleanarch.infra.persistence.repository.StudentJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.teacher.TeacherJpaRepository;
import sptech.school.v2.cleanarch.infra.persistence.repository.appointment.AppointmentJpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

@Component
@Profile("prod")
public class DataLoader implements CommandLineRunner {

    private final TeacherJpaRepository teacherRepository;
    private final StudentJpaRepository studentRepository;
    private final AppointmentJpaRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;

    private final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public DataLoader(TeacherJpaRepository teacherRepository,
                      StudentJpaRepository studentRepository,
                      AppointmentJpaRepository appointmentRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.appointmentRepository = appointmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (teacherRepository.count() == 0) {
            List<Teacher> teachers = new ArrayList<>();

            Teacher t1 = new Teacher("Prof. Carlos Lima", "carlos.lima.prof@email.com",
                    "746.130.980-33", passwordEncoder.encode("senha123"), List.of(Subject.MATHEMATICS, Subject.PHYSICS, Subject.SCIENCE));
            t1.setHourlyRate(75.5);
            t1.setResumeTeacher("Especialista em matemática para ensino médio e pré-vestibular, com 10 anos de experiência.");
            t1.setYearsExperience("10 anos");
            t1.setAcademicFormation("Mestrado em Matemática Aplicada - USP");
            teachers.add(t1);

            Teacher t2 = new Teacher("Prof. Beatriz Costa", "beatriz.costa.prof@email.com",
                    "046.304.470-32", passwordEncoder.encode("senha123"), List.of(Subject.CHEMISTRY, Subject.PHYSICS, Subject.BIOLOGY));
            t2.setHourlyRate(85.0);
            t2.setResumeTeacher("Doutora em Química e Física, focada em preparação para olimpíadas científicas.");
            t2.setYearsExperience("15 anos");
            t2.setAcademicFormation("Doutorado em Química - UNICAMP");
            teachers.add(t2);

            Teacher t3 = new Teacher("Prof. Fernanda Alvez", "fernanda.alvez.prof@email.com",
                    "075.402.100-95", passwordEncoder.encode("senha123"), List.of(Subject.PORTUGUESE, Subject.LITERACY, Subject.ENGLISH));
            t3.setHourlyRate(60.0);
            t3.setResumeTeacher("Professora de português e redação, ajuda alunos em ENEM e concursos.");
            t3.setYearsExperience("8 anos");
            t3.setAcademicFormation("Licenciatura em Letras - UFRJ");
            teachers.add(t3);

            Teacher t4 = new Teacher("Prof. Rodrigo Santos", "rodrigo.santos.prof@email.com",
                    "105.087.170-78", passwordEncoder.encode("senha123"), List.of(Subject.PHYSICS, Subject.MATHEMATICS, Subject.CHEMISTRY));
            t4.setHourlyRate(95.0);
            t4.setResumeTeacher("Professor de física com forte background em pesquisa experimental.");
            t4.setYearsExperience("12 anos");
            t4.setAcademicFormation("Doutorado em Física - USP");
            teachers.add(t4);

            Teacher t5 = new Teacher("Prof. Marina Oliveira", "marina.oliveira.prof@email.com",
                    "687.493.230-67", passwordEncoder.encode("senha123"), List.of(Subject.CHEMISTRY, Subject.SCIENCE, Subject.BIOLOGY));
            t5.setHourlyRate(70.0);
            t5.setResumeTeacher("Ensina química e ciências, foco em práticas laboratoriais seguras.");
            t5.setYearsExperience("9 anos");
            t5.setAcademicFormation("Mestrado em Química - UFMG");
            teachers.add(t5);

            Teacher t6 = new Teacher("Prof. Gustavo Pereira", "gustavo.pereira.prof@email.com",
                    "840.175.750-99", passwordEncoder.encode("senha123"), List.of(Subject.BIOLOGY, Subject.SCIENCE, Subject.CHEMISTRY));
            t6.setHourlyRate(80.0);
            t6.setResumeTeacher("Professor de biologia com experiência em ensino médio e olimpíadas.");
            t6.setYearsExperience("18 anos");
            t6.setAcademicFormation("Doutorado em Biologia - UNICAMP");
            teachers.add(t6);

            Teacher t7 = new Teacher("Prof. Helena Moura", "helena.moura.prof@email.com",
                    "105.315.720-72", passwordEncoder.encode("senha123"), List.of(Subject.ENGLISH, Subject.SPANISH, Subject.PORTUGUESE));
            t7.setHourlyRate(65.0);
            t7.setResumeTeacher("Professora de inglês e espanhol, foco em conversação e gramática.");
            t7.setYearsExperience("11 anos");
            t7.setAcademicFormation("Licenciatura em Letras - PUC");
            teachers.add(t7);

            Teacher t8 = new Teacher("Prof. João Neto", "joao.neto.prof@email.com",
                    "942.489.560-71", passwordEncoder.encode("senha123"), List.of(Subject.GEOGRAPHY, Subject.HISTORY, Subject.SOCIOLOGY));
            t8.setHourlyRate(90.0);
            t8.setResumeTeacher("Professor de geografia e história, especialista em geopolítica.");
            t8.setYearsExperience("20 anos");
            t8.setAcademicFormation("Mestrado em Geografia - UFRJ");
            teachers.add(t8);

            Teacher t9 = new Teacher("Prof. Carla Mendes", "carla.mendes.prof@email.com",
                    "550.434.200-73", passwordEncoder.encode("senha123"), List.of(Subject.ART, Subject.LITERACY));
            t9.setHourlyRate(68.0);
            t9.setResumeTeacher("Professora de artes e alfabetização para séries iniciais.");
            t9.setYearsExperience("7 anos");
            t9.setAcademicFormation("Licenciatura em Educação Artística - UERJ");
            teachers.add(t9);

            Teacher t10 = new Teacher("Prof. Marcos Vinicius", "marcos.vinicius.prof@email.com",
                    "676.706.780-62", passwordEncoder.encode("senha123"), List.of(Subject.PHILOSOPHY, Subject.SOCIOLOGY, Subject.HISTORY));
            t10.setHourlyRate(82.0);
            t10.setResumeTeacher("Professor com formação diversificada em ciências e filosofia.");
            t10.setYearsExperience("14 anos");
            t10.setAcademicFormation("Doutorado em Filosofia da Ciência - USP");
            teachers.add(t10);

            teacherRepository.saveAll(teachers);
        }

        if (studentRepository.count() == 0) {
            List<Student> students = new ArrayList<>();

            Student s1 = new Student("Matheus Alves", "matheus.alves@email.com",
                    "048.043.020-93", passwordEncoder.encode("senha123"));
            s1.setCellphoneNumber("11912345678");
            s1.setDateBirth(LocalDate.of(2008,5,10));
            s1.setSchoolGrade("9º Ano - Ensino Fundamental");
            s1.setSchoolName("Escola Estadual Central");
            Responsible r1 = new Responsible();
            r1.setResponsibleName("Ricardo Alves");
            r1.setKinship("Pai");
            r1.setResponsibleCpf("123.456.789-00");
            r1.setResponsibleCellphoneNumber("11987654321");
            r1.setResponsibleEmail("ricardo.alves@email.com");
            s1.setResponsible(r1);
            students.add(s1);

            Student s2 = new Student("Ana Beatriz Silva", "ana.silva@email.com",
                    "612.325.610-61", passwordEncoder.encode("senha123"));
            s2.setCellphoneNumber("21988776655");
            s2.setDateBirth(LocalDate.of(2006,2,20));
            s2.setSchoolGrade("3º Ano - Ensino Médio");
            s2.setSchoolName("Colégio Particular Progressivo");
            Responsible r2 = new Responsible();
            r2.setResponsibleName("Maria Silva");
            r2.setKinship("Mãe");
            r2.setResponsibleCpf("222.333.444-55");
            r2.setResponsibleCellphoneNumber("21977665544");
            r2.setResponsibleEmail("maria.silva@email.com");
            s2.setResponsible(r2);
            students.add(s2);

            Student s3 = new Student("Lucas Ferreira", "lucas.ferreira@email.com",
                    "042.888.500-45", passwordEncoder.encode("senha123"));
            s3.setCellphoneNumber("11944445555");
            s3.setDateBirth(LocalDate.of(2007,8,02));
            s3.setSchoolGrade("8º Ano - Ensino Fundamental");
            s3.setSchoolName("Escola Municipal Nova Era");
            Responsible r3 = new Responsible();
            r3.setResponsibleName("Paula Ferreira");
            r3.setKinship("Mãe");
            r3.setResponsibleCpf("333.444.555-66");
            r3.setResponsibleCellphoneNumber("11955554444");
            r3.setResponsibleEmail("paula.ferreira@email.com");
            s3.setResponsible(r3);
            students.add(s3);

            Student s4 = new Student("Mariana Costa", "mariana.costa@email.com",
                    "679.351.970-08", passwordEncoder.encode("senha123"));
            s4.setCellphoneNumber("21999998888");
            s4.setDateBirth(LocalDate.of(2009,1,15));
            s4.setSchoolGrade("7º Ano - Ensino Fundamental");
            s4.setSchoolName("Escola Estadual Central");
            Responsible r4 = new Responsible();
            r4.setResponsibleName("João Costa");
            r4.setKinship("Pai");
            r4.setResponsibleCpf("444.555.666-77");
            r4.setResponsibleCellphoneNumber("21988887777");
            r4.setResponsibleEmail("joao.costa@email.com");
            s4.setResponsible(r4);
            students.add(s4);

            Student s5 = new Student("Gabriel Rocha", "gabriel.rocha@email.com",
                    "507.331.300-38", passwordEncoder.encode("senha123"));
            s5.setCellphoneNumber("11922223333");
            s5.setDateBirth(LocalDate.of(2005,2,05));
            s5.setSchoolGrade("2º Ano - Ensino Médio");
            s5.setSchoolName("Colégio Estadual Alpha");
            Responsible r5 = new Responsible();
            r5.setResponsibleName("Roberto Rocha");
            r5.setKinship("Pai");
            r5.setResponsibleCpf("555.666.777-88");
            r5.setResponsibleCellphoneNumber("11933332222");
            r5.setResponsibleEmail("roberto.rocha@email.com");
            s5.setResponsible(r5);
            students.add(s5);

            Student s6 = new Student("Isabela Martins", "isabela.martins@email.com",
                    "749.943.170-38", passwordEncoder.encode("senha123"));
            s6.setCellphoneNumber("21944443333");
            s6.setDateBirth(LocalDate.of(2006,7,30));
            s6.setSchoolGrade("3º Ano - Ensino Médio");
            s6.setSchoolName("Colégio Particular Progressivo");
            Responsible r6 = new Responsible();
            r6.setResponsibleName("Sônia Martins");
            r6.setKinship("Mãe");
            r6.setResponsibleCpf("666.777.888-99");
            r6.setResponsibleCellphoneNumber("21933331111");
            r6.setResponsibleEmail("sonia.martins@email.com");
            s6.setResponsible(r6);
            students.add(s6);

            Student s7 = new Student("Rafael Gomes", "rafael.gomes@email.com",
                    "029.249.460-26", passwordEncoder.encode("senha123"));
            s7.setCellphoneNumber("11977778888");
            s7.setDateBirth(LocalDate.of(2007,3,21));
            s7.setSchoolGrade("8º Ano - Ensino Fundamental");
            s7.setSchoolName("Instituto São Lucas");
            Responsible r7 = new Responsible();
            r7.setResponsibleName("Cecília Gomes");
            r7.setKinship("Mãe");
            r7.setResponsibleCpf("777.888.999-00");
            r7.setResponsibleCellphoneNumber("11966665555");
            r7.setResponsibleEmail("cecilia.gomes@email.com");
            s7.setResponsible(r7);
            students.add(s7);

            Student s8 = new Student("Larissa Pereira", "larissa.pereira@email.com",
                    "662.982.980-88", passwordEncoder.encode("senha123"));
            s8.setCellphoneNumber("21922224444");
            s8.setDateBirth(LocalDate.of(2008,9,12));
            s8.setSchoolGrade("9º Ano - Ensino Fundamental");
            s8.setSchoolName("Escola Municipal Nova Era");
            Responsible r8 = new Responsible();
            r8.setResponsibleName("Helena Pereira");
            r8.setKinship("Mãe");
            r8.setResponsibleCpf("888.999.000-11");
            r8.setResponsibleCellphoneNumber("21955556666");
            r8.setResponsibleEmail("helena.pereira@email.com");
            s8.setResponsible(r8);
            students.add(s8);

            Student s9 = new Student("Pedro Albuquerque", "pedro.albuquerque@email.com",
                    "668.540.040-47", passwordEncoder.encode("senha123"));
            s9.setCellphoneNumber("11911113333");
            s9.setDateBirth(LocalDate.of(2005,4,25));
            s9.setSchoolGrade("2º Ano - Ensino Médio");
            s9.setSchoolName("Colégio Estadual Alpha");
            Responsible r9 = new Responsible();
            r9.setResponsibleName("Marcos Albuquerque");
            r9.setKinship("Pai");
            r9.setResponsibleCpf("999.000.111-22");
            r9.setResponsibleCellphoneNumber("11944442222");
            r9.setResponsibleEmail("marcos.albuquerque@email.com");
            s9.setResponsible(r9);
            students.add(s9);

            Student s10 = new Student("Beatriz Ramos", "beatriz.ramos@email.com",
                    "933.171.820-91", passwordEncoder.encode("senha123"));
            s10.setCellphoneNumber("21910101010");
            s10.setDateBirth(LocalDate.of(2009,6,18));
            s10.setSchoolGrade("7º Ano - Ensino Fundamental");
            s10.setSchoolName("Escola Estadual Central");
            Responsible r10 = new Responsible();
            r10.setResponsibleName("Renata Ramos");
            r10.setKinship("Mãe");
            r10.setResponsibleCpf("111.222.333-44");
            r10.setResponsibleCellphoneNumber("21912121212");
            r10.setResponsibleEmail("renata.ramos@email.com");
            s10.setResponsible(r10);
            students.add(s10);

            studentRepository.saveAll(students);
        }

        // Cria appointments/aulas (parecido com o JSON)
        if (appointmentRepository.count() == 0) {
            List<Appointment> appointments = new ArrayList<>();

            List<Student> savedStudents = studentRepository.findAll();
            List<Teacher> savedTeachers = teacherRepository.findAll();

            java.util.function.Function<String, Student> findStudentByEmail = email ->
                    savedStudents.stream()
                            .filter(s -> email.equalsIgnoreCase(s.getEmail()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Student not found: " + email));

            java.util.function.Function<String, Teacher> findTeacherByName = name ->
                    savedTeachers.stream()
                            .filter(t -> name.equalsIgnoreCase(t.getName()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("Teacher not found: " + name));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("matheus.alves@email.com"),
                    findTeacherByName.apply("Prof. Carlos Lima"),
                    "2025-11-20T10:00:00", 90.0, AppointmentStatus.SCHEDULED, "Online", 113.25, PaymentStatus.PENDING, "MATHEMATICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("ana.silva@email.com"),
                    findTeacherByName.apply("Prof. Beatriz Costa"),
                    "2025-11-21T14:00:00", 120.0, AppointmentStatus.COMPLETED, "Presencial", 170.00, PaymentStatus.PAID, "CHEMISTRY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("matheus.alves@email.com"),
                    findTeacherByName.apply("Prof. Beatriz Costa"),
                    "2025-11-22T09:00:00", 60.0, AppointmentStatus.CANCELLED, "Online", 85.00, PaymentStatus.CANCELLED, "PHYSICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("lucas.ferreira@email.com"),
                    findTeacherByName.apply("Prof. Fernanda Alvez"),
                    "2025-11-23T11:00:00", 60.0, AppointmentStatus.COMPLETED, "Online", 60.00, PaymentStatus.PAID, "PORTUGUESE"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("mariana.costa@email.com"),
                    findTeacherByName.apply("Prof. Rodrigo Santos"),
                    "2025-11-24T09:30:00", 90.0, AppointmentStatus.SCHEDULED, "Presencial", 142.5, PaymentStatus.PENDING, "MATHEMATICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("gabriel.rocha@email.com"),
                    findTeacherByName.apply("Prof. Marina Oliveira"),
                    "2025-11-24T16:00:00", 120.0, AppointmentStatus.COMPLETED, "Presencial", 140.00, PaymentStatus.PAID, "BIOLOGY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("isabela.martins@email.com"),
                    findTeacherByName.apply("Prof. Gustavo Pereira"),
                    "2025-11-25T10:00:00", 60.0, AppointmentStatus.SCHEDULED, "Online", 80.00, PaymentStatus.PENDING, "SCIENCE"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("rafael.gomes@email.com"),
                    findTeacherByName.apply("Prof. Helena Moura"),
                    "2025-11-25T13:00:00", 90.0, AppointmentStatus.COMPLETED, "Online", 97.5, PaymentStatus.PAID, "ENGLISH"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("larissa.pereira@email.com"),
                    findTeacherByName.apply("Prof. João Neto"),
                    "2025-11-26T15:00:00", 120.0, AppointmentStatus.SCHEDULED, "Presencial", 180.00, PaymentStatus.PENDING, "HISTORY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("pedro.albuquerque@email.com"),
                    findTeacherByName.apply("Prof. Carla Mendes"),
                    "2025-11-26T09:00:00", 60.0, AppointmentStatus.COMPLETED, "Presencial", 68.00, PaymentStatus.PAID, "ART"));

            // restantes do JSON (mantidos como exemplo)
            appointments.add(buildAppointment(
                    findStudentByEmail.apply("beatriz.ramos@email.com"),
                    findTeacherByName.apply("Prof. Marcos Vinicius"),
                    "2025-11-27T11:30:00", 90.0, AppointmentStatus.CANCELLED, "Online", 123.00, PaymentStatus.CANCELLED, "PHILOSOPHY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("matheus.alves@email.com"),
                    findTeacherByName.apply("Prof. Fernanda Alvez"),
                    "2025-11-28T10:00:00", 120.0, AppointmentStatus.SCHEDULED, "Online", 120.00, PaymentStatus.PENDING, "PORTUGUESE"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("ana.silva@email.com"),
                    findTeacherByName.apply("Prof. Rodrigo Santos"),
                    "2025-11-28T14:00:00", 60.0, AppointmentStatus.COMPLETED, "Presencial", 95.00, PaymentStatus.PAID, "CHEMISTRY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("lucas.ferreira@email.com"),
                    findTeacherByName.apply("Prof. Marina Oliveira"),
                    "2025-11-29T09:00:00", 90.0, AppointmentStatus.COMPLETED, "Presencial", 105.00, PaymentStatus.PAID, "BIOLOGY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("mariana.costa@email.com"),
                    findTeacherByName.apply("Prof. Gustavo Pereira"),
                    "2025-11-29T15:00:00", 120.0, AppointmentStatus.SCHEDULED, "Online", 160.00, PaymentStatus.PENDING, "SCIENCE"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("gabriel.rocha@email.com"),
                    findTeacherByName.apply("Prof. Helena Moura"),
                    "2025-11-30T10:00:00", 60.0, AppointmentStatus.COMPLETED, "Online", 65.00, PaymentStatus.PAID, "ENGLISH"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("isabela.martins@email.com"),
                    findTeacherByName.apply("Prof. João Neto"),
                    "2025-12-01T13:00:00", 90.0, AppointmentStatus.SCHEDULED, "Presencial", 135.00, PaymentStatus.PENDING, "HISTORY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("rafael.gomes@email.com"),
                    findTeacherByName.apply("Prof. Carla Mendes"),
                    "2025-12-02T09:00:00", 120.0, AppointmentStatus.COMPLETED, "Presencial", 136.00, PaymentStatus.PAID, "ART"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("larissa.pereira@email.com"),
                    findTeacherByName.apply("Prof. Marcos Vinicius"),
                    "2025-12-02T11:00:00", 60.0, AppointmentStatus.SCHEDULED, "Online", 8120.00, PaymentStatus.PENDING, "MATHEMATICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("pedro.albuquerque@email.com"),
                    findTeacherByName.apply("Prof. Carlos Lima"),
                    "2025-12-03T14:00:00", 90.0, AppointmentStatus.COMPLETED, "Online", 113.25, PaymentStatus.PAID, "PHYSICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("beatriz.ramos@email.com"),
                    findTeacherByName.apply("Prof. Beatriz Costa"),
                    "2025-12-03T16:00:00", 120.0, AppointmentStatus.SCHEDULED, "Presencial", 170.00, PaymentStatus.PENDING, "CHEMISTRY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("matheus.alves@email.com"),
                    findTeacherByName.apply("Prof. Rodrigo Santos"),
                    "2025-12-04T09:00:00", 60.0, AppointmentStatus.COMPLETED, "Presencial", 95.00, PaymentStatus.PAID, "MATHEMATICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("ana.silva@email.com"),
                    findTeacherByName.apply("Prof. Marina Oliveira"),
                    "2025-12-04T11:00:00", 90.0, AppointmentStatus.CANCELLED, "Online", 105.00, PaymentStatus.CANCELLED, "BIOLOGY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("lucas.ferreira@email.com"),
                    findTeacherByName.apply("Prof. Gustavo Pereira"),
                    "2025-12-05T10:30:00", 60.0, AppointmentStatus.SCHEDULED, "Online", 80.00, PaymentStatus.PENDING, "SCIENCE"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("mariana.costa@email.com"),
                    findTeacherByName.apply("Prof. Helena Moura"),
                    "2025-12-05T14:30:00", 120.0, AppointmentStatus.COMPLETED, "Online", 130.00, PaymentStatus.PAID, "PORTUGUESE"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("gabriel.rocha@email.com"),
                    findTeacherByName.apply("Prof. João Neto"),
                    "2025-12-06T09:00:00", 60.0, AppointmentStatus.SCHEDULED, "Presencial", 90.00, PaymentStatus.PENDING, "HISTORY"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("isabela.martins@email.com"),
                    findTeacherByName.apply("Prof. Carla Mendes"),
                    "2025-12-06T11:00:00", 90.0, AppointmentStatus.COMPLETED, "Presencial", 102.00, PaymentStatus.PAID, "ART"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("rafael.gomes@email.com"),
                    findTeacherByName.apply("Prof. Marcos Vinicius"),
                    "2025-12-07T10:00:00", 90.0, AppointmentStatus.SCHEDULED, "Online", 123.00, PaymentStatus.PENDING, "MATHEMATICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("larissa.pereira@email.com"),
                    findTeacherByName.apply("Prof. Carlos Lima"),
                    "2025-12-08T15:00:00", 120.0, AppointmentStatus.COMPLETED, "Online", 156.00, PaymentStatus.PENDING, "PHYSICS"));

            appointments.add(buildAppointment(
                    findStudentByEmail.apply("pedro.albuquerque@email.com"),
                    findTeacherByName.apply("Prof. Beatriz Costa"),
                    "2025-12-09T09:00:00", 90.0, AppointmentStatus.SCHEDULED, "Online", 127.50, PaymentStatus.PENDING, "CHEMISTRY"));

            appointmentRepository.saveAll(appointments);
        }
    }

    private Appointment buildAppointment(Student student, Teacher teacher,
                                         String isoDateTime, double duration,
                                         AppointmentStatus status, String location,
                                         double totalValue, PaymentStatus paymentStatus,
                                         String subject) {
        Appointment a = new Appointment();
        a.setStudent(student);
        a.setTeacher(teacher);
        a.setDateTime(LocalDateTime.parse(isoDateTime, dtf));
        a.setLessonDuration(duration);
        a.setStatus(status);
        a.setLocation(location);
        a.setTotalValue(totalValue);
        a.setPaymentStatus(paymentStatus);
        a.setSubject(subject);
        return a;
    }
}
