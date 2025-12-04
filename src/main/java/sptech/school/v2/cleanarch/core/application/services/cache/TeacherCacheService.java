package sptech.school.v2.cleanarch.core.application.services.cache;

import jakarta.annotation.PostConstruct;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.v2.cleanarch.core.application.facades.teacher.TeacherFacadeContract;
import sptech.school.v2.cleanarch.core.application.mappers.TeacherMapper;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherPageResponseDTO;
import sptech.school.v2.cleanarch.core.dtos.out.teacher.TeacherResponseDTO;
import sptech.school.v2.cleanarch.domain.entities.Teacher;

import java.util.List;

/**
 * Serviço para gerenciamento e leitura de cache de Teacher.
 * <p>
 * Os métodos anotados com cache retornam DTOs/entidades serializáveis, evitando
 * qualquer tentativa de serializar {@code ResponseEntity} no Redis.
 */
@Service
public class TeacherCacheService {

    private static final String TEACHER_CACHE_NAME = "teacher";
    private final CacheManager cacheManager;
    private final TeacherFacadeContract teacherFacade;
    private final TeacherMapper teacherMapper;

    public TeacherCacheService(CacheManager cacheManager,
                               TeacherFacadeContract teacherFacade,
                               TeacherMapper teacherMapper) {
        this.cacheManager = cacheManager;
        this.teacherFacade = teacherFacade;
        this.teacherMapper = teacherMapper;
    }

    @PostConstruct
    public void clearLegacyTeacherCache() {
        // Remove qualquer entrada antiga que possa ter sido serializada como ResponseEntity
        clearTeacherCache();
    }

    @Cacheable(cacheNames = TEACHER_CACHE_NAME, key = "#id", unless = "#result == null")
    public TeacherResponseDTO getTeacherById(Integer id) {
        Teacher found = teacherFacade.findById(id);
        return found != null ? teacherMapper.toDtoResponse(found) : null;
    }

    @Cacheable(cacheNames = TEACHER_CACHE_NAME, key = "#page + '_' + #size", unless = "#result == null")
    public TeacherPageResponseDTO listTeachers(int page, int size) {
        int pageNumber = Math.max(page, 0);
        int pageSize = Math.max(size, 1);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        var teachers = teacherFacade.listAll(pageable);
        List<TeacherResponseDTO> dtos = teachers.getContent()
                .stream()
                .map(teacherMapper::toDtoResponse)
                .toList();

        // Retorna um DTO serializável ao invés de PageImpl para evitar problemas de desserialização no Redis
        return new TeacherPageResponseDTO(
                dtos,
                teachers.getNumber(),
                teachers.getSize(),
                teachers.getTotalElements(),
                teachers.getTotalPages(),
                teachers.isLast()
        );
    }

    /**
     * Limpa todo o cache de professores.
     *
     * Útil quando há operações em lote ou quando dados precisam ser
     * completamente sincronizados com o banco de dados.
     */
    public void clearTeacherCache() {
        var cache = cacheManager.getCache(TEACHER_CACHE_NAME);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * Invalida o cache de um professor específico.
     *
     * @param teacherId Identificador do professor
     */
    public void invalidateTeacherCache(Integer teacherId) {
        var cache = cacheManager.getCache(TEACHER_CACHE_NAME);
        if (cache != null) {
            cache.evict(teacherId);
        }
    }

    /**
     * Invalida o cache de lista paginada de professores.
     *
     * @param page Número da página
     * @param size Tamanho da página
     */
    public void invalidateTeacherListCache(int page, int size) {
        var cache = cacheManager.getCache(TEACHER_CACHE_NAME);
        if (cache != null) {
            cache.evict(page + "_" + size);
        }
    }

    /**
     * Verifica se o cache de um professor está ativo.
     *
     * @param teacherId Identificador do professor
     * @return true se há valor em cache, false caso contrário
     */
    public boolean isTeacherCached(Integer teacherId) {
        var cache = cacheManager.getCache(TEACHER_CACHE_NAME);
        if (cache != null) {
            return cache.get(teacherId) != null;
        }
        return false;
    }
}

