package sptech.school.application.usecase;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.adapters.out.persistence.JpaResourceFileRepository;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.adapters.out.persistence.StudentRepositoryJpa;
import sptech.school.adapters.out.persistence.TeacherRepositoryJpa;
import sptech.school.v2.cleanarch.core.application.mappers.ResourceFileMapper;
import sptech.school.domain.dto.response.ResourceFileResponseDTO;
import sptech.school.v2.cleanarch.core.application.gateways.email.EmailSenderGateway;
import sptech.school.v2.cleanarch.domain.entities.PasswordResetToken;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.entities.User;
import sptech.school.v2.cleanarch.domain.exception.AuthenticationException;
import sptech.school.v2.cleanarch.domain.exception.EmailAlreadyExistsException;
import sptech.school.v2.cleanarch.domain.exception.UserDontHaveProfilePhoto;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import sptech.school.v2.cleanarch.infra.persistence.repository.PasswordResetTokenRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;

public abstract class AbstractUserUseCase<T extends User, DTO> implements UserUseCase<T, DTO> {
        protected final JpaUserRepository<T> repository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private EmailSenderGateway emailSenderGateway;

    @Autowired
    @Qualifier("s3StorageService")
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

    @Transactional  
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

    public ResourceFile getProfileImage(Integer id) throws IOException {
        T userTarget = repository.findById(id)
                .orElseThrow(() -> new UserNullException("User not found"));

        ResourceFile profileImage = userTarget.getProfileImage();
        if (profileImage == null) {
            throw new UserDontHaveProfilePhoto("Profile image not found for user.");
        }

        Optional<InputStream> streamOpt = storageService.findFile(profileImage.getFileLocation());
        if (streamOpt.isEmpty()) {
            throw new IOException("File not found in S3 storage.");
        }

        profileImage.setInputStream(streamOpt.get());
        return profileImage;
    }

    public ResourceFileResponseDTO uploadProfileImage(MultipartFile file, Integer id) throws IOException {
        T userTarget = repository.findById(id)
                .orElseThrow(() -> new UserNullException("User not found"));

        ResourceFile oldProfileImage = userTarget.getProfileImage();
        if (oldProfileImage != null) {
            // remove o arquivo antigo do Amazon S3, caso ele já exista
            storageService.deleteFile(oldProfileImage.getFileLocation());
            jpaResourceFileRepository.deleteById(oldProfileImage.getId());
        }

        String location = storageService.saveFile(file);
        ResourceFile resourceFile = new ResourceFile(
                file.getOriginalFilename()
                , file.getContentType()
                , location
                , file.getSize()
        );
        userTarget.setProfileImage(resourceFile);
        repository.save(userTarget);
        ResourceFile savedFile = jpaResourceFileRepository.save(resourceFile);
        return resourceFileMapper.toResponse(savedFile);
    }

    public void resetPassword(String email, String newPassword) {
        Optional<T> optionalUser = repository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNullException("User not found with email: " + email);
        }
        T user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }

    public void sendResetCode(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo ou vazio.");
        }

        Optional<T> optionalUser = repository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNullException("Usuário não encontrado com o e-mail: " + email);
        }

        String code = generateCode(); // você precisa ter esse método ou passá-lo como dependência
        PasswordResetToken token = new PasswordResetToken(email, code, LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(token);

        emailSenderGateway.send(email, "Código de Redefinição de Senha", "Seu código é: " + code);
    }

    private String generateCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);  // Gera um código de 6 dígitos
    }
}