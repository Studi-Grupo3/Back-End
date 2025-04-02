package sptech.school.application.usecase;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import sptech.school.adapters.out.persistence.JpaResourceFileRepository;
import sptech.school.adapters.out.persistence.JpaUserRepository;
import sptech.school.application.mappers.ResourceFileMapper;
import sptech.school.application.service.JwtService;
import sptech.school.domain.dto.request.LoginRequestDTO;
import sptech.school.domain.dto.response.ResourceFileResponseDTO;
import sptech.school.domain.entity.ResourceFile;
import sptech.school.domain.entity.User;
import sptech.school.domain.exception.UserException;

import java.io.IOException;
import java.time.LocalDateTime;

public abstract class AbstractUserUseCase<T extends User, DTO> implements UserUseCase<T, DTO> {
    protected final JpaUserRepository<T> repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StorageServiceUseCase storageService;

    @Autowired
    private JpaResourceFileRepository jpaResourceFileRepository;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    public AbstractUserUseCase(JpaUserRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public T update(@Valid DTO dto, Integer id) {
        T userTarget = repository.findById(id)
            .orElseThrow(() -> new UserException("User not found"));

        userTarget = validateSpecify(dto, userTarget);

        return repository.save(userTarget);
    }

    @Override
    public T login(@Valid LoginRequestDTO user) {
        T foundUser = repository.findByEmailIgnoreCase(user.email());

        if (foundUser == null && user.cpf() != null) {
            foundUser = repository.findByCpf(user.cpf());
        }

        if (foundUser == null || !passwordEncoder.matches(user.password(), foundUser.getPassword())) {
            throw new UserException("Invalid email/CPF or password");
        }

        foundUser.setLastLogin(LocalDateTime.now());
        return foundUser;
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