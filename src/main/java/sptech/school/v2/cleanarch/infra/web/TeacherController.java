package sptech.school.v2.cleanarch.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.domain.entity.Teacher;
import sptech.school.v2.cleanarch.core.application.facades.teacher.TeacherFacadeContract;
import sptech.school.v2.cleanarch.core.application.mappers.TeacherMapper;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherRegisterDTO;
import sptech.school.v2.cleanarch.core.dtos.in.teacher.TeacherUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherResponseDTO;

import java.util.List;

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
    @Operation(summary = "Cria um novo professor", description = "Recebe os dados de um professor e o cadastra no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<TeacherResponseDTO> createUser(@RequestBody @Valid TeacherRegisterDTO dto) {
        Teacher teacher = teacherFacade.create(teacherMapper.toEntity(dto));

        return ResponseEntity.status(201).body(teacherMapper.toDtoResponse(teacher));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera um professor por ID", description = "Retorna os dados de um professor dado seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Integer id) {
        TeacherResponseDTO dto = teacherMapper.toDtoResponse(teacherFacade.findById(id));
        return ResponseEntity.status(200).body(dto);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta um professor por ID", description = "Remove um professor do sistema dado seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<Void> deleteTeacher(@PathVariable Integer id) {
        teacherFacade.delete(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um professor", description = "Recebe os dados atualizados de um professor e os salva no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Integer id, @RequestBody @Valid TeacherUpdateDTO dto) {
        /* Mapeando os dados do DTO para a entidade existente
         e ignorando os campos que estão como nulos e enviando para a service */
        Teacher teacherUpdated = teacherFacade.findById(id);
        teacherMapper.updateTeacherFromDto(dto, teacherUpdated);
        Teacher updatedTeacher = teacherFacade.update(teacherUpdated, id);
        return ResponseEntity.status(200).body(teacherMapper.toDtoResponse(updatedTeacher));
    }


    @GetMapping
    @Operation(summary = "Lista todos os professores", description = "Retorna uma lista com todos os professores cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de professores retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum professor encontrado")
    })
    public ResponseEntity<List<TeacherResponseDTO>> findAllTeachers() {
        List<TeacherResponseDTO> dtos = teacherFacade.listAll().stream()
                .map(teacherMapper::toDtoResponse).toList();
        return ResponseEntity.status(200).body(dtos);
    }
}