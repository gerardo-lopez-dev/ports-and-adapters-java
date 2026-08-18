# Template: ports-and-adapters-java

> Skeleton Java + Spring Boot con arquitectura hexagonal (Ports & Adapters), single-module Maven,
> Postgres real (dev = prod), migraciones versionadas con Flyway, Resilience4j, tracing y métricas.

Stack: Java 21, Spring Boot 3.3.x, Maven (single module), Postgres 16 (Docker Compose), Flyway,
Resilience4j 2.2.x, Micrometer Tracing (OpenTelemetry), Micrometer + Prometheus.

---

## Estructura del proyecto

```
ports-and-adapters-java/
├── pom.xml
├── docker-compose.yml                         ← Postgres, mismo servicio en dev y prod
├── src/main/java/com/example/orders/
│   ├── PortsAndAdaptersApplication.java
│   ├── domain/
│   │   ├── Order.java
│   │   └── Money.java
│   ├── application/
│   │   ├── CreateOrderUseCase.java
│   │   ├── OrderRepositoryPort.java
│   │   ├── PaymentGatewayPort.java
│   │   └── PaymentResult.java
│   └── infrastructure/
│       ├── BeansConfig.java
│       └── adapters/
│           ├── in/web/OrderController.java
│           └── out/
│               ├── jpa/
│               │   ├── OrderJpaEntity.java
│               │   ├── OrderJpaRepository.java
│               │   └── JpaOrderRepositoryAdapter.java     ← @Profile("!test"), default real
│               ├── memory/InMemoryOrderRepository.java     ← @Profile("test"), solo para tests
│               └── payment/FakePaymentGatewayAdapter.java  ← CB + Retry + Bulkhead + @PreDestroy
└── src/main/resources/
    ├── application.yml
    └── db/migration/V1__create_orders_table.sql            ← Admin process (Flyway)
```

---

## Auditoría 12-factor (estado tras esta versión)

| # | Factor | Estado | Cómo se cumple |
| --- | --- | --- | --- |
| 1 | Codebase | ✅ | Un repo Git, mismo código para local/dev/prod |
| 2 | Dependencies | ✅ | Todo declarado en `pom.xml`, nada del sistema anfitrión |
| 3 | Config | ✅ | `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` vienen de variables de entorno; el YAML solo trae defaults de conveniencia para desarrollo local, no secretos de producción |
| 4 | Backing services | ✅ | Postgres es un recurso conectable vía `OrderRepositoryPort`; cambiar de proveedor es cambiar la URL, no el código |
| 5 | Build, release, run | ✅ | `mvn clean install` (build) separado de `mvn spring-boot:run` con env vars inyectadas al arrancar (release = paquete + config) |
| 6 | Processes (stateless) | ✅ | `InMemoryOrderRepository` queda detrás de `@Profile("test")`, no puede activarse en dev/prod por accidente; todo el estado persistente vive en Postgres |
| 7 | Port binding | ✅ | Tomcat embebido, puerto 8080 autocontenido |
| 8 | Concurrency | ⚠️ Fuera del código | Responsabilidad del orquestador (réplicas de contenedor); correcto que no esté en el código Java |
| 9 | Disposability | ✅ | `server.shutdown: graceful` + `@PreDestroy` en `FakePaymentGatewayAdapter` cerrando recursos antes de terminar |
| 10 | Dev/prod parity | ✅ | Postgres corre igual en dev (`docker-compose.yml`) y prod (misma imagen/tecnología, solo cambia la URL) — ya no hay mismatch H2/Postgres |
| 11 | Logs | ✅ | Todo a `stdout` vía `logging.pattern.console`, sin `FileAppender` |
| 12 | Admin processes | ✅ | Flyway aplica `V1__create_orders_table.sql` como proceso de migración versionado; Hibernate ya no genera schema (`ddl-auto: validate`) |

11 de 12 factores cumplen dentro del código; el 8 correctamente queda como decisión de infraestructura/orquestador.

---

## Notas de diseño

- **Resilience4j** protege `PaymentGatewayPort`, no el repositorio — CB/Retry/Bulkhead tienen sentido sobre una llamada a un servicio externo real, no sobre la propia base de datos de la app.
- **Tracing** usa el exporter `logging` de OpenTelemetry (spans van a consola) para no depender de un collector externo corriendo. Cambiar a `opentelemetry-exporter-otlp` cuando haya Jaeger/Tempo disponible.
- **Prometheus** queda expuesto en `/actuator/prometheus` sin configuración adicional.
- El perfil `test` existe únicamente para poder correr pruebas sin levantar Postgres; nunca se activa en dev ni en prod.

---

## Comandos

```bash
docker compose up -d
mvn clean install
mvn spring-boot:run
```

```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/prometheus
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"id":"1","amount":100.0,"currency":"USD","customerId":"cust-1"}'
```
