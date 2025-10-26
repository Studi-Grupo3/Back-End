package sptech.school.v2.cleanarch.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherRegisterDTO;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Teacher;
import sptech.school.v2.cleanarch.core.application.facades.teacher.TeacherFacadeContract;
import sptech.school.v2.cleanarch.core.application.mappers.TeacherMapper;

import java.util.List;

@Tag(name = "Teachers", description = "Operações de CRUD para professores")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherFacadeContract teacherFacade;
    private final TeacherMapper teacherMapper;

    public TeacherController(TeacherFacadeContract teacherFacade, TeacherMapper teacherMapper) {
        this.teacherFacade = teacherFacade;
        this.teacherMapper = teacherMapper;
    }

    @PostMapping
    @Operation(
            summary = "Cria um novo professor",
            description = "Recebe os dados de um professor, aplica validação (@Valid) e o cadastra no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erros de validação)"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados (e-mail/CPF já cadastrado)")
    })
    public ResponseEntity<TeacherResponseDTO> createUser(@RequestBody @Valid TeacherRegisterDTO dto) {
        Teacher toCreate = teacherMapper.toEntity(dto);
        Teacher created = teacherFacade.create(toCreate);
        return ResponseEntity.status(201).body(teacherMapper.toDtoResponse(created));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera um professor por ID",
            description = "Retorna os dados de um professor baseado em seu identificador numérico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<TeacherResponseDTO> getTeacherById(
            @Parameter(name = "id", description = "Identificador único do professor", required = true)
            @PathVariable Integer id
    ) {
        Teacher found = teacherFacade.findById(id);
        return ResponseEntity.ok(teacherMapper.toDtoResponse(found));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Deleta um professor por ID",
            description = "Remove permanentemente o professor identificado pelo ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<Void> deleteTeacher(
            @Parameter(name = "id", description = "Identificador único do professor", required = true)
            @PathVariable Integer id
    ) {
        teacherFacade.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza um professor",
            description = "Atualiza os dados de um professor existente com base no ID informado. Campos nulos no DTO podem ser ignorados pelo mapper."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erros de validação)"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<TeacherResponseDTO> updateTeacher(
            @Parameter(name = "id", description = "Identificador único do professor", required = true)
            @PathVariable Integer id,
            @RequestBody @Valid TeacherUpdateDTO dto
    ) {
        Teacher entity = teacherFacade.findById(id);
        teacherMapper.updateTeacherFromDto(dto, entity);
        Teacher updated = teacherFacade.update(entity, id);
        return ResponseEntity.ok(teacherMapper.toDtoResponse(updated));
    }

    @GetMapping
    @Operation(
            summary = "Lista professores",
            description = "Retorna professores paginados. Pode retornar lista vazia."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<Page<TeacherResponseDTO>> findAllTeachers(
            @Parameter(description = "Página a ser recuperada", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Quantidade de itens por página", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        int pageNumber = Math.max(page, 0);
        int pageSize = Math.max(size, 1);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Teacher> teachers = teacherFacade.listAll(pageable);
        List<TeacherResponseDTO> dtos = teachers.getContent()
                .stream()
                .map(teacherMapper::toDtoResponse)
                .toList();
        Page<TeacherResponseDTO> dtoPage = new PageImpl<>(dtos, teachers.getPageable(), teachers.getTotalElements());
        return ResponseEntity.ok(dtoPage);
    }

}
