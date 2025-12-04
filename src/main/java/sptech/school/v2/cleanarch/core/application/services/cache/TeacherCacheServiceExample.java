package sptech.school.v2.cleanarch.core.application.services.cache;

import org.springframework.stereotype.Component;

/**
 * Exemplo de uso do TeacherCacheService.
 *
 * Este arquivo demonstra como usar o serviço de cache programaticamente
 * em casos onde você precisa de controle manual sobre invalidação e limpeza.
 *
 * IMPORTANTE: Este é apenas um arquivo de EXEMPLO E DOCUMENTAÇÃO.
 * Ele NÃO é usado na aplicação e pode ser ignorado/deletado.
 */
@Component
class TeacherCacheServiceExample {

    private TeacherCacheService cacheService;

    /**
     * Exemplo 1: Limpar todo o cache de Teachers
     *
     * Use case: Quando há operações em lote que modificam muitos professores
     */
    public void limparCacheCompleto() {
        // Limpa TODAS as chaves de professor
        cacheService.clearTeacherCache();

        // Agora todas as próximas requisições consultarão o BD
    }

    /**
     * Exemplo 2: Invalidar cache de um professor específico
     *
     * Use case: Quando você quer manter cache de outros professores
     * mas invalida apenas um
     */
    public void invalidarUmProfessor(Integer teacherId) {
        // Remove do cache apenas a chave "teacher::1"
        cacheService.invalidateTeacherCache(teacherId);

        // Outros professores continuam em cache
    }

    /**
     * Exemplo 3: Invalidar cache de lista paginada
     *
     * Use case: Quando você quer invalida apenas uma página da lista
     */
    public void invalidarPaginaEspecifica(int page, int size) {
        // Remove do cache apenas a chave "teacher::0_10"
        cacheService.invalidateTeacherListCache(page, size);

        // Outras páginas continuam em cache
    }

    /**
     * Exemplo 4: Verificar se dados estão cacheados
     *
     * Use case: Monitoramento ou logging
     */
    public void verificarSeTemCache(Integer teacherId) {
        boolean estaCacheado = cacheService.isTeacherCached(teacherId);

        if (estaCacheado) {
            System.out.println("Professores " + teacherId + " está em cache ✓");
        } else {
            System.out.println("Professores " + teacherId + " NÃO está em cache ✗");
        }
    }

    /**
     * Exemplo 5: Usar em um controller customizado
     *
     * Este é o padrão de uso recomendado
     */
    public void exemploNoController() {
        // Em um @PostMapping de operação em lote
        // for (int i = 0; i < 1000; i++) {
        //     updateTeacherFromBatch(i);
        // }
        // Depois de atualizar todos
        // cacheService.clearTeacherCache();
    }

    /**
     * Exemplo 6: Combinação - Invalidação seletiva
     *
     * Cenário: Atualizar um professor e manter cache de outros
     */
    public void updateTeacherComInvalidacaoSeletiva(Integer teacherId) {
        // 1. Atualizar no BD
        // repository.save(teacher);

        // 2. Invalidar apenas este professor do cache
        cacheService.invalidateTeacherCache(teacherId);

        // Outros professores continuam em cache
        // Próximas requisições de outros IDs = cache hit
        // Próxima requisição deste ID = BD
    }

    /**
     * Exemplo 7: Cache health check
     *
     * Para monitoramento e debugging
     */
    public void healthCheck() {
        System.out.println("=== Cache Health Check ===");

        for (int i = 1; i <= 5; i++) {
            boolean cached = cacheService.isTeacherCached(i);
            System.out.println("Teacher " + i + ": " + (cached ? "✓ CACHED" : "✗ NOT CACHED"));
        }
    }
}

/**
 * RESUMO DE USO
 *
 * 1. Para a maioria dos casos, as anotações @Cacheable e @CacheEvict
 *    na TeacherController já cuidam de tudo automaticamente.
 *
 * 2. Use TeacherCacheService apenas se precisar de:
 *    - Controle manual de cache
 *    - Invalidação seletiva
 *    - Monitoramento
 *    - Lógica complexa de invalidação
 *
 * 3. Exemplo de injeção:
 *
 *    @RestController
 *    public class MeuController {
 *        @Autowired
 *        private TeacherCacheService cacheService;
 *
 *        @PostMapping("/bulk-update")
 *        public void atualizarEmLote() {
 *            // ... fazer atualizações ...
 *            cacheService.clearTeacherCache();
 *        }
 *    }
 */

