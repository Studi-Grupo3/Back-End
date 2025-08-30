package sptech.school.v2.cleanarch.infra.teacher.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.v2.cleanarch.core.teacher.dtos.out.TeacherResponseDTO;
import sptech.school.v2.cleanarch.core.teacher.application.TeacherRegisterUseCase;
import sptech.school.v2.cleanarch.core.teacher.dtos.in.TeacherRegisterDTO;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRegisterUseCase teacherRegisterUseCase;

    public TeacherController(TeacherRegisterUseCase teacherRegisterUseCase) {
        this.teacherRegisterUseCase = teacherRegisterUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria um novo professor", description = "Recebe os dados de um professor e o cadastra no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<TeacherResponseDTO> createUser(@RequestBody @Valid TeacherRegisterDTO dto) {
        TeacherResponseDTO teacher = teacherRegisterUseCase.create(dto);

        return ResponseEntity.status(201).body(teacher);
    }
}