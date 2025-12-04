# 🏗️ Arquitetura - Spring Cache com Redis

## Diagrama de Fluxo

```
┌─────────────────────────────────────────────────────────────────────┐
│                          CLIENTE HTTP                               │
└─────────────────────────────┬───────────────────────────────────────┘
                              │
                    GET /api/teachers/1
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    TeacherController                                │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │ @GetMapping("/{id}")                                         │  │
│  │ @Cacheable(cacheNames="teacher", key="#id")                 │  │
│  │ public ResponseEntity getTeacherById(@PathVariable id) { ... }  │
│  └──────────────────────────────────────────────────────────────┘  │
└─────────────────────────────┬───────────────────────────────────────┘
                              │
                    Spring Cache Aspect
                    (Intercepta requisição)
                              │
                    ┌─────────┴──────────┐
                    │                    │
                    ▼                    ▼
         ┌──────────────────┐  ┌─────────────────────┐
         │  Redis Cache     │  │  Cache Miss?        │
         │  (Memory)        │  │                     │
         │                  │  │  SIM → Consultar BD │
         │  key: teacher::1 │  │  NÃO → Retornar    │
         │  TTL: 1 hour     │  └─────────────────────┘
         └──────────┬───────┘           │
                    │                   ▼
                    │         ┌──────────────────────┐
                    │         │   JPA Repository     │
                    │         │   (Database)         │
                    │         │                      │
                    │         │  SELECT * FROM       │
                    │         │  Teacher WHERE id=1  │
                    │         └──────────┬───────────┘
                    │                    │
                    │                    ▼
                    │         ┌──────────────────────┐
                    │         │   Armazenar em Cache │
                    │         │   (Serializar)       │
                    └────────►│   teacher::1         │
                              │   TTL: 1 hour        │
                              └──────────┬───────────┘
                                         │
                                         ▼
                              ┌──────────────────────┐
                              │   Retornar ao Client │
                              │   Response 200 OK    │
                              └──────────────────────┘
```

---

## Arquitetura em Camadas

```
┌────────────────────────────────────────────────────────────┐
│                    Presentation Layer                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         TeacherController                            │  │
│  │  @GetMapping    @PostMapping    @PutMapping         │  │
│  │  @Cacheable     @CacheEvict     @CacheEvict         │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────┘
                              │
┌────────────────────────────────────────────────────────────┐
│                   Application Layer                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         TeacherFacadeContract                        │  │
│  │  - create()    - findById()                          │  │
│  │  - update()    - listAll()                           │  │
│  │  - delete()                                          │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         TeacherCacheService                          │  │
│  │  - clearTeacherCache()                               │  │
│  │  - invalidateTeacherCache(id)                        │  │
│  │  - isTeacherCached(id)                               │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────┘
                              │
┌────────────────────────────────────────────────────────────┐
│                    Domain Layer                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         Teacher Entity                               │  │
│  │  - id: Integer                                       │  │
│  │  - name: String                                      │  │
│  │  - email: String                                     │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────┘
                              │
┌────────────────────────────────────────────────────────────┐
│                  Infrastructure Layer                      │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         Spring Cache (Redis Backend)                 │  │
│  │  - @EnableCaching Configuration                      │  │
│  │  - RedisCacheManager                                 │  │
│  │  - Jedis Connection Factory                          │  │
│  └──────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────┐  │
│  │         JPA Repository (Database)                    │  │
│  │  - Spring Data JPA                                   │  │
│  │  - MySQL Connection                                  │  │
│  └──────────────────────────────────────────────────────┘  │
└────────────────────────────────────────────────────────────┘
```

---

## Fluxo de Operações

### 1. GET Endpoint (Leitura com Cache)

```
┌─────────────────────────────────────────────────────┐
│  GET /api/teachers/1                               │
│  Authorization: Bearer token                       │
└────────────────┬────────────────────────────────────┘
                 │
                 ▼
      ┌──────────────────────────┐
      │  Spring Security         │
      │  Validar Token           │
      └────────────┬─────────────┘
                   │
                   ▼
      ┌──────────────────────────┐
      │  Spring Cache Interceptor│
      │  (Aspect)                │
      └────────────┬─────────────┘
                   │
         ┌─────────┴──────────┐
         │                    │
      Cache Hit?             Cache Miss?
         │                    │
         │ SIM               │ NÃO
         │                    │
         ▼                    ▼
    Redis Get            TeacherFacade
    (5ms)                (200ms)
         │                    │
         └────────┬───────────┘
                  │
                  ▼
         ┌──────────────────────────┐
         │  Serializar Resposta     │
         │  (DTO Mapper)            │
         └────────────┬─────────────┘
                      │
                      ▼
         ┌──────────────────────────┐
         │  Response 200 OK         │
         │  TeacherResponseDTO      │
         └──────────────────────────┘
```

### 2. PUT Endpoint (Escrita com Invalidação)

```
┌─────────────────────────────────────────────────────┐
│  PUT /api/teachers/1                               │
│  Authorization: Bearer token                       │
│  Body: { "name": "Novo Nome" }                     │
└────────────────┬────────────────────────────────────┘
                 │
                 ▼
      ┌──────────────────────────┐
      │  Spring Security         │
      │  Validar Token           │
      └────────────┬─────────────┘
                   │
                   ▼
      ┌──────────────────────────┐
      │  @Valid DTO Validation   │
      │  (Hibernat Validator)    │
      └────────────┬─────────────┘
                   │
                   ▼
      ┌──────────────────────────┐
      │  TeacherFacade.update()  │
      │  (Atualiza BD)           │
      └────────────┬─────────────┘
                   │
                   ▼
      ┌──────────────────────────┐
      │  @CacheEvict             │
      │  (AOP Aspect)            │
      └────────────┬─────────────┘
                   │
                   ▼
      ┌──────────────────────────┐
      │  Redis Clear             │
      │  KEYS teacher::*         │
      │  DEL teacher::*          │
      └────────────┬─────────────┘
                   │
                   ▼
         ┌──────────────────────────┐
         │  Response 200 OK         │
         │  TeacherResponseDTO      │
         │  (Dados atualizados)     │
         └──────────────────────────┘
```

---

## Componentes Implementados

```
Spring Boot Application
│
├── @Configuration Classes
│   └── CacheConfig
│       └── @EnableCaching
│
├── @Controller Classes
│   └── TeacherController
│       ├── @GetMapping("/{id}")
│       │   └── @Cacheable(key="#id")
│       │
│       ├── @GetMapping
│       │   └── @Cacheable(key="#page+'_'+#size")
│       │
│       ├── @PostMapping
│       │   └── @CacheEvict(allEntries=true)
│       │
│       ├── @PutMapping("/{id}")
│       │   └── @CacheEvict(allEntries=true)
│       │
│       └── @DeleteMapping("/{id}")
│           └── @CacheEvict(allEntries=true)
│
├── @Service Classes
│   └── TeacherCacheService
│       ├── clearTeacherCache()
│       ├── invalidateTeacherCache(id)
│       ├── invalidateTeacherListCache(page, size)
│       └── isTeacherCached(id)
│
├── @Component Annotations
│   ├── @Cacheable (Spring)
│   ├── @CacheEvict (Spring)
│   └── CacheManager (Auto-injected)
│
└── External Services
    ├── Redis (Cache Store)
    │   └── Jedis Client
    │
    └── MySQL Database
        └── JPA Repository
```

---

## Mapeamento de Cache

```
┌─────────────────────────────────────────────────────┐
│           SPRING CACHE KEYS                        │
└─────────────────────────────────────────────────────┘

Cache Name: "teacher"
Prefix: "studi:"
TTL: 3600000ms (1 hour)

┌─────────────────────────────────────────────────────┐
│  GET /api/teachers/1                              │
│  ↓                                                  │
│  Cache Key: studi:teacher::1                       │
│  Type: Object (TeacherResponseDTO)                 │
│  TTL: Expires in 3600 seconds                      │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│  GET /api/teachers?page=0&size=10                  │
│  ↓                                                  │
│  Cache Key: studi:teacher::0_10                    │
│  Type: Object (Page<TeacherResponseDTO>)           │
│  TTL: Expires in 3600 seconds                      │
└─────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────┐
│  PUT /api/teachers/1                               │
│  ↓                                                  │
│  @CacheEvict: Remove all keys matching pattern     │
│  Padrão: studi:teacher::*                          │
│  Efeito: Cache miss em próximas requisições        │
└─────────────────────────────────────────────────────┘
```

---

## Sequence Diagram - Cache Hit

```
Client      Spring Boot     Spring Cache   Redis       DB
  │              │              │           │           │
  ├─ GET /1 ────►│              │           │           │
  │              │              │           │           │
  │              ├─ Intercept ──►│           │           │
  │              │   @Cacheable  │           │           │
  │              │              │           │           │
  │              │              ├─ GET key─►│           │
  │              │              │◄──  Value ─┤           │
  │              │              │ (Cache Hit)│           │
  │              │◄─ TeacherDTO ─┤           │           │
  │◄─ 200 OK ────┤              │           │           │
  │  (5ms)       │              │           │           │
  │              │              │           │           │
```

---

## Sequence Diagram - Cache Miss

```
Client      Spring Boot     Spring Cache   Redis       DB
  │              │              │           │           │
  ├─ GET /1 ────►│              │           │           │
  │              │              │           │           │
  │              ├─ Intercept ──►│           │           │
  │              │   @Cacheable  │           │           │
  │              │              │           │           │
  │              │              ├─ GET key─►│           │
  │              │              │◄─ nil ────┤           │
  │              │              │(Cache Miss)│          │
  │              │              │           │           │
  │              ├─────────────────────────────┤          │
  │              │  Execute Method             │          │
  │              │                             │          │
  │              ├─ findById(1) ──────────────►│ Query   │
  │              │                             │          │
  │              │◄──────────── TeacherDTO ────┤          │
  │              │              │           │           │
  │              ├─ Serialize ──►│           │           │
  │              │   & Store    ├─ SET key ─►│           │
  │              │              │           │           │
  │◄─ 200 OK ────┤              │           │           │
  │  (200ms)     │              │           │           │
  │              │              │           │           │
```

---

## Lifecycle do Cache

```
Application Start
    │
    ▼
CacheConfig Initialized
    │
    ├─ @EnableCaching registered
    ├─ Spring Cache Aspect activated
    ├─ Redis ConnectionFactory created
    └─ Jedis Pool initialized
    │
    ▼
TeacherController Ready
    │
    ├─ First Request
    │  └─ Cache Miss → BD → Store in Redis
    │
    ├─ Subsequent Requests (< 1 hour)
    │  └─ Cache Hit → Redis → Instant Response
    │
    ├─ Update/Delete Operation
    │  ├─ Execute in BD
    │  └─ @CacheEvict → Clear Cache
    │
    └─ TTL Expiration (after 1 hour)
       └─ Data auto-deleted from Redis
          Next request → Cache Miss → BD

```

---

## Monitoramento Redis

```
Redis Memory Usage Pattern:

┌─────────────────────────────────────────┐
│  Memory (MB)                            │
│  │                                      │
│  │     ▄▄▄▄▄▄▄▄▄▄▄                    │
│  │   ▄▀               ▀▄                │
│  │  ▀                  ▀▄               │
│  │                       ▀▄            │
│  │                         ▀▄▄▄▄▄▄▄▄  │
│  │                                    │
│  └─────────────────────────────────────┘
│  0  10  20  30  40  50  60  70  80  90  100
│                 Tempo (min)
│
│  Picos = GETs (dados adicionados)
│  Quedas = TTL expiração (dados deletados)
│  Novo pico = Novo ciclo de cache
```

---

## Performance Comparison

```
┌──────────────────────────────────────────┐
│ Tempo de Resposta (ms)                   │
│                                          │
│ SEM CACHE:                               │
│ ████████████████████ 150ms              │
│                                          │
│ COM CACHE (hit):                         │
│ ██ 5ms                                   │
│                                          │
│ COM CACHE (miss):                        │
│ ████████████████ 120ms                   │
│ (um pouco mais rápido que sem cache)     │
│                                          │
│ Melhoria: 30x mais rápido em cache hit! │
└──────────────────────────────────────────┘
```

---

**Diagrama criado para facilitar compreensão da arquitetura implementada.**

