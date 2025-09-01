package sptech.school.v2.cleanarch.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.domain.entity.Student;
import sptech.school.v2.cleanarch.core.application.facades.StudentFacadeContract;
import sptech.school.v2.cleanarch.core.application.mappers.StudentMapper;
import sptech.school.v2.cleanarch.core.dtos.in.StudentRegisterDTO;
import sptech.school.v2.cleanarch.core.dtos.in.StudentUpdateDTO;
import sptech.school.v2.cleanarch.core.dtos.out.StudentResponseDTO;

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
    @Operation(summary = "Cria um novo estudante", description = "Recebe os dados de um estudante e o cadastra no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<StudentResponseDTO> createUser(@RequestBody @Valid StudentRegisterDTO dto) {
        Student student = studentFacade.create(studentMapper.toEntity(dto));

        return ResponseEntity.status(201).body(studentMapper.toDtoResponse(student));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera um estudante por ID", description = "Retorna os dados de um estudante dado seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante encontrado"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Integer id) {
        StudentResponseDTO dto = studentMapper.toDtoResponse(studentFacade.findById(id));
        return ResponseEntity.status(200).body(dto);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deleta um estudante por ID", description = "Remove um estudante do sistema dado seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estudante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        studentFacade.delete(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um estudante", description = "Recebe os dados atualizados de um estudante e os salva no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Estudante não encontrado")
    })
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Integer id, @RequestBody @Valid StudentUpdateDTO dto) {
        /* Mapeando os dados do DTO para a entidade existente
         e ignorando os campos que estão como nulos e enviando para a service */
        Student studentUpdated = studentFacade.findById(id);
        studentMapper.updateStudentFromDto(dto, studentUpdated);
        Student student = studentFacade.update(studentUpdated, id);
        return ResponseEntity.status(200).body(studentMapper.toDtoResponse(student));
    }


    @GetMapping
    @Operation(summary = "Lista todos os estudantes", description = "Retorna uma lista com todos os estudantes cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estudantes retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum estudante encontrado")
    })
    public ResponseEntity<java.util.List<StudentResponseDTO>> findAllStudents() {
        java.util.List<StudentResponseDTO> dtos = studentFacade.listAll().stream()
                .map(studentMapper::toDtoResponse).toList();
        return ResponseEntity.status(200).body(dtos);
    }
}
