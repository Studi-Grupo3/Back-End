//package sptech.school.v2.cleanarch.config;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.CacheErrorHandler;
//import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * Configuração de Cache com Redis para a aplicação Studi.
// *
// * O cache usa {@link GenericJackson2JsonRedisSerializer} serializando valores
// * como {@code Object}, garantindo que DTOs/entidades sejam armazenados em vez de
// * {@code ResponseEntity} (que não deve ser colocado no cache).
// */
//@Configuration
//@EnableCaching
//public class CacheConfig {
//
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();
//
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer));
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        return RedisCacheManager.builder(redisConnectionFactory)
//                .cacheDefaults(redisCacheConfiguration())
//                .build();
//    }
//
//    @Bean
//    public CacheErrorHandler cacheErrorHandler() {
//        // Evita que entradas antigas (ex.: PageImpl/ResponseEntity) causem erros na desserialização.
//        // Ao encontrar erro no cache, removemos a chave problemática e seguimos executando o método.
//        return new SimpleCacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException exception, org.springframework.cache.Cache cache, Object key) {
//                evictQuietly(cache, key);
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException exception, org.springframework.cache.Cache cache, Object key, Object value) {
//                evictQuietly(cache, key);
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException exception, org.springframework.cache.Cache cache, Object key) {
//                evictQuietly(cache, key);
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException exception, org.springframework.cache.Cache cache) {
//                evictQuietly(cache, null);
//            }
//
//            private void evictQuietly(org.springframework.cache.Cache cache, Object key) {
//                if (cache == null) {
//                    return;
//                }
//                try {
//                    if (key != null) {
//                        cache.evict(key);
//                    } else {
//                        cache.clear();
//                    }
//                } catch (RuntimeException ignored) {
//                    // Evita que o erro de serialização impeça a leitura do endpoint.
//                }
//            }
//        };
//    }
//}
//
//
