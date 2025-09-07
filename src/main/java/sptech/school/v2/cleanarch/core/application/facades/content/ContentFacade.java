package sptech.school.v2.cleanarch.core.application.facades.content;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.v2.cleanarch.core.application.facades.student.StudentFacadeContract;
import sptech.school.v2.cleanarch.core.application.usecases.content.ContentUseCase;
import sptech.school.v2.cleanarch.domain.entities.Content;
import sptech.school.v2.cleanarch.domain.entities.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ContentFacade implements ContentFacadeContract {
    private final ContentUseCase contentUseCase;
    private final StudentFacadeContract studentFacade;

    public ContentFacade(ContentUseCase contentUseCase, StudentFacadeContract studentFacade) {
        this.contentUseCase = contentUseCase;
        this.studentFacade = studentFacade;
    }

    @Override
    public Content uploadFile(MultipartFile file, Integer studentId) throws IOException {
        Student student = studentFacade.findById(studentId);
        return contentUseCase.uploadFile(file, student);
    }

    @Override
    public Optional<Content> getMetadata(Long id) {
        return contentUseCase.getMetadata(id);
    }

    @Override
    public Optional<InputStream> downloadFile(Long id) throws IOException {
        return contentUseCase.downloadFile(id);
    }
}
