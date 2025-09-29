package sptech.school.v2.cleanarch.core.application.facades.student;

import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Student;
import sptech.school.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.usecases.command.StudentCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.StudentQueryUseCase;
import sptech.school.v2.cleanarch.core.application.utils.VerifyEmailAndCpfUtil;

import java.util.List;

@Service
public class StudentFacade implements StudentFacadeContract {
    private final StudentCommandUseCase studentCommandUseCase;
    private final StudentQueryUseCase studentQueryUseCase;
    private final VerifyEmailAndCpfUtil verifyEmailAndCpfUtil;

    public StudentFacade(StudentCommandUseCase studentCommandUseCase, StudentQueryUseCase studentQueryUseCase, VerifyEmailAndCpfUtil verifyEmailAndCpfUtil) {
        this.studentCommandUseCase = studentCommandUseCase;
        this.studentQueryUseCase = studentQueryUseCase;
        this.verifyEmailAndCpfUtil = verifyEmailAndCpfUtil;
    }

    @Override
    public Student create(Student student) {
        verifyEmailAndCpfUtil.verify(student);
        return studentCommandUseCase.create(student);
    }

    @Override
    public List<Student> listAll() {
        return studentQueryUseCase.listAll();
    }

    @Override
    public Student update(Student student, Integer id) {
        verifyEmailAndCpfUtil.verify(student);
        student.setId(id);
        return studentCommandUseCase.update(student);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            throw new UserNullException("Student dont exist");
        }
        studentCommandUseCase.delete(id);
    }

    @Override
    public Student findById(Integer id) {
        return studentQueryUseCase.findById(id);
    }

    @Override
    public Boolean studentExistsByEmail(String email) {
        return studentQueryUseCase.studentExistsByEmail(email);
    }

    @Override
    public Boolean studentExistsByCpf(String cpf) {
        return studentQueryUseCase.studentExistsByCpf(cpf);
    }

}