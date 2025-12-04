package sptech.school.v2.cleanarch.config.annotations;

import org.springframework.cache.annotation.CacheEvict;

import java.lang.annotation.*;

/**
 * Anotação customizada para invalidar o cache de Teacher.
 *
 * Uso: @CacheEvictTeacher
 *
 * Esta anotação limpa o cache quando um professor é atualizado ou deletado.
 * Deve ser usada em métodos que modificam dados de Teacher.
 *
 * Exemplo de uso:
 * - Em métodos de atualização de Professor
 * - Em métodos de deleção de Professor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@CacheEvict(cacheNames = "teacher", allEntries = true)
@Documented
public @interface CacheEvictTeacher {
}

