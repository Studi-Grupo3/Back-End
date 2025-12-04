package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

/**
 * Representa uma resposta paginada serializável para professores.
 * <p>
 * Mantemos apenas dados simples para que o Redis armazene DTOs, evitando
 * serializar {@code PageImpl}, que não possui construtor padrão.
 * <p>
 * IMPORTANTE: Possui construtor padrão e anotações Jackson para desserialização
 * correta do Redis (GenericJackson2JsonRedisSerializer).
 */
public class TeacherPageResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TeacherResponseDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    /**
     * Construtor padrão (sem argumentos).
     * NECESSÁRIO para Jackson desserializar do Redis.
     */
    public TeacherPageResponseDTO() {
    }

    /**
     * Construtor com argumentos - usado pela aplicação.
     * Anotado com @JsonCreator para Jackson reconhecer como criador alternativo.
     */
    @JsonCreator
    public TeacherPageResponseDTO(
            @JsonProperty("content") List<TeacherResponseDTO> content,
            @JsonProperty("pageNumber") int pageNumber,
            @JsonProperty("pageSize") int pageSize,
            @JsonProperty("totalElements") long totalElements,
            @JsonProperty("totalPages") int totalPages,
            @JsonProperty("last") boolean last) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    // Getters
    public List<TeacherResponseDTO> getContent() {
        return content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isLast() {
        return last;
    }

    // Setters (necessários para deserialização)
    public void setContent(List<TeacherResponseDTO> content) {
        this.content = content;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}

