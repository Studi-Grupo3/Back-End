package sptech.school.v2.cleanarch.core.application.facades.teacher;

import org.springframework.stereotype.Service;
import sptech.school.domain.entity.Teacher;
import sptech.school.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.core.application.usecases.command.TeacherCommandUseCase;
import sptech.school.v2.cleanarch.core.application.usecases.query.TeacherQueryUseCase;
import sptech.school.v2.cleanarch.core.application.utils.VerifyEmailAndCpfUtil;

import java.util.List;

@Service
public class TeacherFacade implements TeacherFacadeContract {
    private final TeacherCommandUseCase teacherCommandUseCase;
    private final TeacherQueryUseCase teacherQueryUseCase;
    private final VerifyEmailAndCpfUtil verifyEmailAndCpfUtil;

    public TeacherFacade(TeacherCommandUseCase teacherCommandUseCase, TeacherQueryUseCase teacherQueryUseCase, VerifyEmailAndCpfUtil verifyEmailAndCpfUtil) {
        this.teacherCommandUseCase = teacherCommandUseCase;
        this.teacherQueryUseCase = teacherQueryUseCase;
        this.verifyEmailAndCpfUtil = verifyEmailAndCpfUtil;
    }

    @Override
    public Teacher create(Teacher teacher) {
        verifyEmailAndCpfUtil.verify(teacher);
        return teacherCommandUseCase.create(teacher);
    }

    @Override
    public Boolean teacherExistsByEmail(String email) {
        return teacherQueryUseCase.teacherExistsByEmail(email);
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherQueryUseCase.findById(id);
    }

    @Override
    public Teacher update(Teacher teacher, Integer id) {
        verifyEmailAndCpfUtil.verify(teacher);
        teacher.setId(id);
        return teacherCommandUseCase.update(teacher);
    }

    @Override
    public void delete(Integer id) {
        if (findById(id) == null) {
            throw new UserNullException("Teacher dont exist");
        }
        teacherCommandUseCase.delete(id);
    }

    @Override
    public Boolean teacherExistsByCpf(String cpf) {
        return teacherQueryUseCase.teacherExistsByCpf(cpf);
    }

    @Override
    public List<Teacher> listAll() {
        return teacherQueryUseCase.listAll();
    }
}