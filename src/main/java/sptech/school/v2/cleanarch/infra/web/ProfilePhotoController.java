package sptech.school.v2.cleanarch.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.domain.dto.response.ResourceFileResponseDTO;
import sptech.school.v2.cleanarch.core.application.facades.student.StudentFacadeContract;
import sptech.school.v2.cleanarch.core.application.facades.teacher.TeacherFacadeContract;
import sptech.school.v2.cleanarch.domain.entities.ResourceFile;
import sptech.school.v2.cleanarch.domain.enumerated.Role;

import java.io.IOException;

@Tag(name = "Profile Photos", description = "Operações para upload e download de fotos de perfil")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/profile-photos")
public class ProfilePhotoController {

    private final StudentFacadeContract studentFacade;
    private final TeacherFacadeContract teacherFacade;

    public ProfilePhotoController(StudentFacadeContract studentFacade, TeacherFacadeContract teacherFacade) {
        this.studentFacade = studentFacade;
        this.teacherFacade = teacherFacade;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Envia a foto de perfil de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto de perfil enviada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Arquivo inválido ou role desconhecido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<ResourceFileResponseDTO> uploadProfilePhoto(
            @Parameter(description = "Arquivo da foto de perfil", required = true)
            @RequestPart("file") MultipartFile file,
            @Parameter(description = "Identificador do usuário", required = true)
            @RequestParam("id") Integer userId,
            @Parameter(description = "Role do usuário (student ou teacher)", required = true)
            @RequestParam("role") String role
    ) throws IOException {
        Role userRole = resolveRole(role);
        ResourceFileResponseDTO dto = switch (userRole) {
            case STUDENT -> studentFacade.uploadProfileImage(file, userId);
            case TEACHER -> teacherFacade.uploadProfileImage(file, userId);
        };
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Recupera a foto de perfil de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto de perfil encontrada"),
            @ApiResponse(responseCode = "400", description = "Role desconhecido"),
            @ApiResponse(responseCode = "404", description = "Usuário ou foto não encontrados")
    })
    public ResponseEntity<InputStreamResource> getProfilePhoto(
            @Parameter(description = "Identificador do usuário", required = true)
            @RequestParam("id") Integer userId,
            @Parameter(description = "Role do usuário (student ou teacher)", required = true)
            @RequestParam("role") String role
    ) throws IOException {
        Role userRole = resolveRole(role);
        ResourceFile profileImage = switch (userRole) {
            case STUDENT -> studentFacade.getProfileImage(userId);
            case TEACHER -> teacherFacade.getProfileImage(userId);
        };

        InputStreamResource resource = new InputStreamResource(profileImage.getInputStream());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(profileImage.getFileType()))
                .contentLength(profileImage.getFileSize())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + profileImage.getFileName() + "\"")
                .body(resource);
    }

    private Role resolveRole(String role) {
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role must be provided");
        }
        return switch (role.trim().toLowerCase()) {
            case "student" -> Role.STUDENT;
            case "teacher" -> Role.TEACHER;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown role: " + role);
        };
    }

}
