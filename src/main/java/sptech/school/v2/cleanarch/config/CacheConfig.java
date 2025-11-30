package sptech.school.v2.cleanarch.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de Cache com Redis para a aplicação Studi.
 *
 * Esta classe ativa o suporte a cache na aplicação.
 * O Spring Boot autoconfiguration cuida de conectar com Redis
 * baseado nas propriedades configuradas em application.properties.
 *
 * O cache é utilizado nos endpoints de Teacher para otimizar a performance.
 *
 * Configuração esperada em application.properties:
 * - spring.cache.type=redis
 * - spring.redis.host=localhost
 * - spring.redis.port=6379
 * - spring.cache.redis.time-to-live=3600000
 */
@Configuration
@EnableCaching
public class CacheConfig {
    // Spring Boot autoconfiguration cuida de tudo
    // Nenhuma bean customizada necessária
}


