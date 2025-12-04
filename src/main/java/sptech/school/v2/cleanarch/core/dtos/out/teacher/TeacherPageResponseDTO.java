package sptech.school.v2.cleanarch.core.dtos.out.teacher;

import java.io.Serializable;
import java.util.List;

/**
 * Representa uma resposta paginada serializável para professores.
 * <p>
 * Mantemos apenas dados simples para que o Redis armazene DTOs, evitando
 * serializar {@code PageImpl}, que não possui construtor padrão.
 */
public class TeacherPageResponseDTO implements Serializable {

    private final List<TeacherResponseDTO> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean last;

    public TeacherPageResponseDTO(List<TeacherResponseDTO> content,
                                  int pageNumber,
                                  int pageSize,
                                  long totalElements,
                                  int totalPages,
                                  boolean last) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

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
}

