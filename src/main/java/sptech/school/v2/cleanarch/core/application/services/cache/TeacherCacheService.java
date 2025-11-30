package sptech.school.v2.cleanarch.core.application.services.cache;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * Serviço para gerenciamento de cache de Teacher.
 *
 * Este serviço encapsula operações de limpeza e invalidação de cache,
 * seguindo os padrões de Clean Architecture e Single Responsibility Principle.
 *
 * Casos de uso:
 * - Invalidar cache de um professor específico
 * - Limpar todo o cache de professores
 * - Consultar estado do cache
 */
@Service
public class TeacherCacheService {

    private static final String TEACHER_CACHE_NAME = "teacher";
    private final CacheManager cacheManager;

    public TeacherCacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
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

