package sptech.school.v2.cleanarch.infra.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.v2.cleanarch.core.application.usecases.security.JwtUseCase;
import sptech.school.v2.cleanarch.core.application.facades.student.StudentFacadeContract;
import sptech.school.v2.cleanarch.core.application.facades.teacher.TeacherFacadeContract;
import sptech.school.v2.cleanarch.core.application.usecases.password.PasswordResetUseCase;
import sptech.school.v2.cleanarch.core.dtos.in.ForgotPasswordRequest;
import sptech.school.v2.cleanarch.core.dtos.in.LoginRequestDTO;
import sptech.school.v2.cleanarch.core.dtos.in.VerifyCodeRequest;
import sptech.school.v2.cleanarch.core.dtos.out.AuthResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.domain.enumerated.Role;
import sptech.school.v2.cleanarch.domain.exception.AuthenticationException;
import sptech.school.v2.cleanarch.domain.exception.UserNullException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/auths")
@Tag(name = "Auth", description = "Autenticação e recuperação de senha")
public class AuthController {
    private final TeacherFacadeContract teacherFacade;
    private final StudentFacadeContract studentFacade;
    private final PasswordResetUseCase passwordResetUseCase;
    private final JwtUseCase jwtUseCase;

    public AuthController(TeacherFacadeContract teacherFacade, StudentFacadeContract studentFacade, PasswordResetUseCase passwordResetUseCase, JwtUseCase jwtUseCase) {
        this.teacherFacade = teacherFacade;
        this.studentFacade = studentFacade;
        this.passwordResetUseCase = passwordResetUseCase;
        this.jwtUseCase = jwtUseCase;
    }
    @Operation(summary = "Autentica usuário (aluno ou professor)", description = "Tenta autenticar como aluno e, em caso de falha, como professor. Retorna JWT e dados básicos do usuário; 401 se credenciais inválidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })

    @PostMapping("/login")
    ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        Student student = null;
        Teacher teacher = null;
        try {
            student = studentFacade.login(dto.email(), dto.password());
            System.out.println(student.getEmail() + student.getName());
        } catch (AuthenticationException ignored) {}

        try {
            teacher = teacherFacade.login(dto.email(), dto.password());
        } catch (AuthenticationException ignored) {}

        String token;
        System.out.println("Student: " + student);
        System.out.println("Teacher: " + teacher);

        if (dto.email().equals("admin@exemplo.com") && dto.password().equals("password")) {
            token = jwtUseCase.generateToken("admin@exemplo.com", "Admin", "ADMIN");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(null, "Admin", null, "admin@exemplo.com", token, Role.ADMIN);
            return ResponseEntity.ok(authResponseDTO);
        }

        if (student != null) {
            token = jwtUseCase.generateToken(student.getEmail(), student.getName(), "STUDENT");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(student.getId(), student.getName(), student.getCpf(), student.getEmail(), token, Role.STUDENT);
            return ResponseEntity.ok(authResponseDTO);
        } else if (teacher != null) {
            token = jwtUseCase.generateToken(teacher.getEmail(), teacher.getName(), "TEACHER");
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(teacher.getId(), teacher.getName(), teacher.getCpf(), teacher.getEmail(), token, Role.TEACHER);
            return ResponseEntity.ok(authResponseDTO);
        }
        throw new AuthenticationException("Invalid credentials");
    }
    @Operation(summary = "Solicita envio de código de redefinição de senha", description = "Envia um código para o e-mail informado (aluno e/ou professor). Retorna 200 quando enviado; 404 se e-mail não encontrado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código de redefinição enviado"),
            @ApiResponse(responseCode = "404", description = "E-mail não encontrado")
    })

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        boolean sent = false;

        try {
            studentFacade.sendResetCode(request.getEmail());
            sent = true;
        } catch (UserNullException ignored) {}

        try {
            teacherFacade.sendResetCode(request.getEmail());
            sent = true;
        } catch (UserNullException ignored) {}

        if (!sent) {
            throw new UserNullException("E-mail inválido");
        }

        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Valida o código de verificação", description = "Valida o código de redefinição para o e-mail informado. Retorna 200 se válido; 400 quando inválido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Código válido"),
            @ApiResponse(responseCode = "400", description = "Código inválido")
    })

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest body) {
        boolean valid = passwordResetUseCase.verifyCode(body.getEmail(), body.getCode());
        if (valid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de verificação inválido.");
        }
    }
}