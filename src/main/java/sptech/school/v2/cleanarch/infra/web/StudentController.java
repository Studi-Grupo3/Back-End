package sptech.school.v2.cleanarch.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.domain.entities.Student;
import sptech.school.v2.cleanarch.core.application.facades.student.StudentFacadeContract;
import sptech.school.v2.cleanarch.core.application.mappers.StudentMapper;
import sptech.school.v2.cleanarch.core.dtos.in.StudentRegisterDTO;
import sptech.school.v2.cleanarch.core.dtos.in.StudentUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.out.StudentResponseDTO;

import java.util.List;

@Tag(name = "Students", description = "Operações de CRUD para estudantes")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentFacadeContract studentFacade;
    private final StudentMapper studentMapper;

    public StudentController(StudentFacadeContract studentFacade, StudentMapper studentMapper) {
        this.studentFacade = studentFacade;
        this.studentMapper = studentMapper;
    }

    @PostMapping
    @Operation(
            summary = "Cria um novo estudante",
            description = "Recebe os dados de um estudante, aplica validação (@Valid) e o cadastra no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erros de validação)"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (e-mail/CPF já cadastrado)")
    })
    public ResponseEntity<StudentResponseDTO> createUser(@RequestBody @Valid StudentRegisterDTO dto) {
        Student toCreate = studentMapper.toEntity(dto);
        Student created = studentFacade.create(toCreate);
        return ResponseEntity.status(201).body(studentMapper.toDtoResponse(created));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera um estudante por ID",
            description = "Retorna os dados de um estudante baseado em seu identificador numérico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @Parameter(name = "id", description = "Identificador único do estudante", required = true)
            @PathVariable Integer id
    ) {
        Student found = studentFacade.findById(id);
        return ResponseEntity.ok(studentMapper.toDtoResponse(found));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deleta um estudante por ID",
            description = "Remove permanentemente o estudante identificado pelo ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estudante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    public ResponseEntity<Void> deleteStudent(
            @Parameter(name = "id", description = "Identificador único do estudante", required = true)
            @PathVariable Integer id
    ) {
        studentFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um estudante",
            description = "Atualiza os dados de um estudante existente com base no ID informado. Campos nulos no DTO podem ser ignorados pelo mapper."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erros de validação)"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @Parameter(name = "id", description = "Identificador único do estudante", required = true)
            @PathVariable Integer id,
            @RequestBody @Valid StudentUpdateDTO dto
    ) {
        Student entity = studentFacade.findById(id);
        studentMapper.updateStudentFromDto(dto, entity);
        Student updated = studentFacade.update(entity, id);
        return ResponseEntity.ok(studentMapper.toDtoResponse(updated));
    }

    @GetMapping
    @Operation(
            summary = "Lista estudantes",
            description = "Retorna uma lista com todos os estudantes cadastrados. Pode retornar lista vazia."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<StudentResponseDTO>> findAllStudents() {
        List<StudentResponseDTO> dtos = studentFacade.listAll()
                .stream()
                .map(studentMapper::toDtoResponse)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
