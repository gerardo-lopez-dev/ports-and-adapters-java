# Graph Report - ports-and-adapters-java  (2026-08-20)

## Corpus Check
- 22 files · ~10,302 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 128 nodes · 154 edges · 17 communities (15 shown, 2 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a04ea6a4`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- The Twelve-Factor App
- mvnw
- The 12 rules
- Template: ports-and-adapters-java
- FlywayMultiSchemaConfigTest.java
- Ports and Adapters - Java
- Factor I: Codebase — Análisis de cumplimiento
- Factor II: Dependencies — Análisis de cumplimiento
- opencode.json
- org.junit.jupiter.api.Test
- graphify.js
- com.example:ports-and-adapters-java
- FlywayMultiSchemaConfig.java
- PortBindingAndDisposabilityTest
- Execution

## God Nodes (most connected - your core abstractions)
1. `The Twelve-Factor App` - 14 edges
2. `The 12 rules` - 13 edges
3. `TwelveFactorArchitectureTest` - 11 edges
4. `FlywayMultiSchemaConfigTest` - 6 edges
5. `FlywayMultiSchemaConfig` - 5 edges
6. `PortBindingAndDisposabilityTest` - 5 edges
7. `Ports and Adapters - Java` - 5 edges
8. `Template: ports-and-adapters-java` - 5 edges
9. `12-Factor App — Golden Rules` - 5 edges
10. `PortsAndAdaptersJavaApplicationTests` - 4 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Import Cycles
- None detected.

## Communities (17 total, 2 thin omitted)

### Community 0 - "The Twelve-Factor App"
Cohesion: 0.13
Nodes (14): 10. Dev/prod parity (Paridad entre desarrollo y producción), 11. Logs (Registros), 12. Admin processes (Procesos administrativos), 1. Codebase (Código base), 2. Dependencies (Dependencias), 3. Config (Configuración), 4. Backing services (Servicios de respaldo), 5. Build, release, run (Build, release, ejecución) (+6 more)

### Community 1 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 2 - "The 12 rules"
Cohesion: 0.11
Nodes (17): 12-Factor App — Golden Rules, Exceptions, Final checklist, I. Codebase, II. Dependencies, III. Config, IV. Backing services, IX. Disposability (+9 more)

### Community 3 - "Template: ports-and-adapters-java"
Cohesion: 0.33
Nodes (5): Auditoría 12-factor (estado tras esta versión), Comandos, Estructura del proyecto, Notas de diseño, Template: ports-and-adapters-java

### Community 4 - "FlywayMultiSchemaConfigTest.java"
Cohesion: 0.23
Nodes (8): javax.sql.DataSource, org.springframework.boot.autoconfigure.SpringBootApplication, org.springframework.boot.test.context.SpringBootTest, org.springframework.test.context.ActiveProfiles, PortsAndAdaptersJavaApplication, FlywayMultiSchemaConfigTest, TestApp, PortsAndAdaptersJavaApplicationTests

### Community 5 - "Ports and Adapters - Java"
Cohesion: 0.33
Nodes (5): License, Ports and Adapters - Java, Requirements, Run, Tests

### Community 6 - "Factor I: Codebase — Análisis de cumplimiento"
Cohesion: 0.40
Nodes (4): Conclusión, Factor I: Codebase — Análisis de cumplimiento, Qué pide el factor, Qué se verificó

### Community 7 - "Factor II: Dependencies — Análisis de cumplimiento"
Cohesion: 0.40
Nodes (4): Conclusión, Factor II: Dependencies — Análisis de cumplimiento, Qué pide el factor, Qué se verificó

### Community 8 - "opencode.json"
Cohesion: 0.50
Nodes (3): plugin, $schema, .opencode/plugins/graphify.js

### Community 9 - "org.junit.jupiter.api.Test"
Cohesion: 0.28
Nodes (3): com.tngtech.archunit.core.domain.JavaClasses, org.junit.jupiter.api.Test, TwelveFactorArchitectureTest

### Community 12 - "FlywayMultiSchemaConfig.java"
Cohesion: 0.29
Nodes (8): org.springframework.boot.CommandLineRunner, org.springframework.boot.context.properties.ConfigurationProperties, org.springframework.boot.context.properties.EnableConfigurationProperties, org.springframework.context.annotation.Bean, org.springframework.context.annotation.Configuration, org.springframework.context.annotation.Profile, FlywayMultiSchemaConfig, FlywayProperties

### Community 13 - "PortBindingAndDisposabilityTest"
Cohesion: 0.39
Nodes (4): GenericContainer, org.testcontainers.containers.GenericContainer, org.testcontainers.junit.jupiter.Testcontainers, PortBindingAndDisposabilityTest

### Community 16 - "Execution"
Cohesion: 0.29
Nodes (6): 1. ArchUnit (always), 2. Testcontainers (only if Docker is running), 3. Report, Execution, Reference, What it checks

## Knowledge Gaps
- **50 isolated node(s):** `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java`, `Reference`, `What it checks` (+45 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **2 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What connects `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java` to the rest of the system?**
  _50 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `The Twelve-Factor App` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._
- **Should `The 12 rules` be split into smaller, more focused modules?**
  _Cohesion score 0.1111111111111111 - nodes in this community are weakly interconnected._