# 12-Factor App — Golden Rules

## Scope

Applies to all Java code under `src/main/java`, especially the `domain`, `application`, and `infrastructure` modules of the hexagonal architecture. Does not cover infrastructure configuration (CI/CD, IaC).

## The 12 rules

### I. Codebase

One repo per app, with multiple deployments from the same build. Do not duplicate code between services by copying folders; extract to a shared library if reuse is needed.

- ❌ Copy/pasting an entire module to another microservice.
- ✅ Extract to a versioned library and import it as a dependency.
- [ ] Does new code live in this repo and not duplicate another?

### II. Dependencies

Declare all dependencies explicitly in `pom.xml`/`build.gradle`. Never assume a library or tool is globally installed on the system.

- ❌ Using an OS utility (`Runtime.exec("convert ...")`) without declaring the binary as a documented dependency.
- ✅ Adding the corresponding Java dependency to `pom.xml`.
- [ ] Is every new dependency declared in the build file?

### III. Config

Configuration that varies between environments (URLs, credentials, flags) goes through environment variables, never hardcoded or committed with real values in `application.properties`.

- ❌ `String url = "jdbc:postgresql://prod-db:5432/app";`
- ✅ `@Value("${DB_URL}") String url;` with defaults only for local.
- [ ] Are no environment-specific values hardcoded?

### IV. Backing services

Databases, queues, and caches are accessed only through a _port_ (interface) defined in `application`, never by instantiating the concrete client inside the domain.

- ❌ Injecting `JdbcTemplate` directly into a domain use case.
- ✅ Defining `interface OrderRepository` in `application`, implementing it in `infrastructure`.
- [ ] Does the domain depend only on interfaces, not concrete clients?

### V. Build, release, run

Clearly separate build (compile), release (build + config), and run (execute). Do not mix compilation with environment configuration in the same step.

- ❌ A script that compiles and deploys while hardcoding target environment config.
- ✅ A single artifact (jar/image), parameterized by environment variables at runtime.
- [ ] Is the same artifact used for all environments, with only config changing?

### VI. Processes

The app runs as one or more _stateless_ processes. No mutable in-memory state (static or instance) that survives between requests.

- ❌ `private static Map<String, Object> cache = new HashMap<>();` in a `@Service`.
- ✅ State persisted in a backing service (Redis, DB), not in process memory.
- [ ] Is there no session/business state stored in static or instance variables?

### VII. Port binding

The app exposes its service via its own port binding (embedded), not depending on an external server injected at runtime.

- ❌ Requiring an externally pre-installed Tomcat on the server.
- ✅ Spring Boot with embedded server, exposing the port via `server.port`.
- [ ] Is the app self-contained and exposing its own port?

### VIII. Concurrency

Scale out by adding processes, not by growing threads within a single monolithic process without limits.

- ❌ Manual unlimited `Thread` creation to handle growing load.
- ✅ Stateless design that allows running N instances behind a load balancer.
- [ ] Does the design allow scaling by adding instances, not internal concurrency hacks?

### IX. Disposability

Fast startup and graceful shutdown. Any listener/consumer must handle `shutdown` by releasing resources correctly.

- ❌ A `@Scheduled` task or consumer that does not release connections on SIGTERM.
- ✅ Implementing `SmartLifecycle`/`@PreDestroy` to close resources in order.
- [ ] Do processes start fast and shut down cleanly on shutdown?

### X. Dev/prod parity

Minimize differences between dev, staging, and prod: same backing services (same DB engine, same broker), not different mocks per environment.

- ❌ H2 in-memory in dev, PostgreSQL in prod.
- ✅ PostgreSQL in all environments (via Docker locally).
- [ ] Are backing services of the same type in all environments?

### XI. Logs

Logs are an event stream to stdout, never managed or written to files by the app itself.

- ❌ Custom `FileWriter` writing logs to a `.log` file on disk.
- ✅ SLF4J/Logback configured for stdout output, infrastructure collects the stream.
- [ ] Does the code use the standard logger and not write log files manually?

### XII. Admin processes

Administrative tasks (migrations, one-off scripts) run as separate processes, with the same code/config as the app, not as ad-hoc logic embedded in the normal flow.

- ❌ A hidden REST endpoint that runs a data migration manually.
- ✅ A separate command/script (e.g., Flyway migration, Spring Boot CLI runner) versioned with the code.
- [ ] Are admin tasks separate, versioned processes, not hidden endpoints?

## Final checklist

- [ ] Codebase: no duplicated code across repos/services
- [ ] Dependencies: everything explicitly declared in the build
- [ ] Config: no hardcoded environment values
- [ ] Backing services: access only through ports/interfaces
- [ ] Build/release/run: same artifact for all environments
- [ ] Processes: no mutable in-process state
- [ ] Port binding: self-contained service with its own port
- [ ] Concurrency: horizontally scalable design
- [ ] Disposability: fast startup, graceful shutdown
- [ ] Dev/prod parity: same backing services in all environments
- [ ] Logs: stdout output via standard logger
- [ ] Admin processes: admin tasks as separate versioned processes

## Exceptions

If a task requires intentionally breaking a rule, mark it explicitly in the code with a comment `// RULE-EXCEPTION: <factor> - <reason>` on the affected line or block. Do not leave it implicit or only in the commit message.

Read and apply this file on every code generation or modification task, unless an exception is documented per the Exceptions section.
