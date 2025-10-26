//package sptech.school.application.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import sptech.school.domain.entity.Content;
//import sptech.school.application.usecase.ContentRepositoryUseCase;
//import sptech.school.application.usecase.StorageServiceUseCase;
//import sptech.school.domain.entity.Student;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Optional;
//
//@Service
//public class ContentService {
//
//    @Autowired
//    private ContentRepositoryUseCase repository;
//    @Autowired
//    @Qualifier("azureStorageService")
//    private StorageServiceUseCase storageService;
//
//    public Content saveFile(MultipartFile file, Student student) throws IOException {
//        String location = storageService.saveFile(file);
//        Content content = new Content(
//                file.getOriginalFilename()
//                , file.getContentType()
//                , location
//                , file.getSize());
//        return repository.save(content);
//    }
//
//    public Optional<Content> getMetadataById(Long id) {
//        return repository.findById(id);
//    }
//
//    public Optional<InputStream> findFileById(Long id) throws IOException {
//        Optional<Content> optionalContent = repository.findById(id);
//
//        if (optionalContent.isEmpty()) {
//            return Optional.empty();
//        }
//
//        Content content = optionalContent.get();
//        return storageService.findFile(content.getFileLocation());
//    }
//}
