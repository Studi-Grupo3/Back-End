package sptech.school.v2.cleanarch.config.annotations;

import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

/**
 * Anotação customizada para cache de resultados de operações de leitura de Professor.
 *
 * Uso: @CacheableTeacher("methodName")
 *
 * Esta anotação encapsula a configuração de cache específica para operações de Teacher,
 * seguindo os padrões de Clean Architecture.
 *
 * Exemplo de uso:
 * - Em métodos que recuperam um professor por ID
 * - Em métodos que listam professores com paginação
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Cacheable(cacheNames = "teacher", unless = "#result == null")
@Documented
public @interface CacheableTeacher {
    /**
     * Nome customizado para a chave do cache.
     * Se não especificado, será gerado automaticamente baseado nos parâmetros do método.
     */
    String value() default "";
}

