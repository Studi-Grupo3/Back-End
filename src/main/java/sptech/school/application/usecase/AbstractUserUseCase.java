package sptech.school.application.usecase;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.adapters.out.persistence.JpaResourceFileRepository;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.adapters.out.persistence.StudentRepositoryJpa;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.application.mappers.ResourceFileMapper;
import sptech.school.domain.dto.response.ResourceFileResponseDTO;
import sptech.school.domain.entity.ResourceFile;
import sptech.school.domain.entity.User;
import sptech.school.domain.exception.AuthenticationException;
import sptech.school.domain.exception.EmailAlreadyExistsException;
import sptech.school.domain.exception.UserNullException;

import java.io.IOException;
import java.util.Optional;

public abstract class AbstractUserUseCase<T extends User, DTO> implements UserUseCase<T, DTO> {
    protected final JpaUserRepository<T> repository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private StorageServiceUseCase storageService;

    @Autowired
    private JpaResourceFileRepository jpaResourceFileRepository;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Autowired
    TeacherRepositoryJpa teacherRepository;

    @Autowired
    StudentRepositoryJpa studentRepository;

    public AbstractUserUseCase(JpaUserRepository<T> repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public T create(@Valid T entity) {
        if (entity == null) throw new UserNullException("The user cannot be null.");
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        if (studentRepository.existsByEmail(entity.getEmail()) || teacherRepository.existsByEmail(entity.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered for another user.");
        }

        return repository.save(entity);
    }

    @Override
    public T update(@Valid DTO dto, Integer id) {
        T userTarget = repository.findById(id)
                .orElseThrow(() -> new UserNullException("User not found"));

        userTarget = validateSpecify(dto, userTarget);

        return repository.save(userTarget);
    }

    @Override
    public T login(String email, String password) {
        Optional<T> foundUser = repository.findByEmail(email);
        if (foundUser.isPresent() && passwordEncoder.matches(password, foundUser.get().getPassword())) {
            return foundUser.get();
        }
        throw new AuthenticationException("Invalid credentials");
    }

    public ResourceFileResponseDTO saveFile(MultipartFile file) throws IOException {
        String location = storageService.saveFile(file);
        ResourceFile resourceFile = new ResourceFile(
                file.getOriginalFilename()
                , file.getContentType()
                , location
                , file.getSize()
        );
        ResourceFile savedFile = jpaResourceFileRepository.save(resourceFile);
        return resourceFileMapper.toResponse(savedFile);
    }
}