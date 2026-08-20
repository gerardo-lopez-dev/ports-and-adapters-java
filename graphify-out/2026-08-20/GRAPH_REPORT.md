# Graph Report - ports-and-adapters-java  (2026-08-20)

## Corpus Check
- Corpus is ~10,740 words - fits in a single context window. You may not need a graph.

## Summary
- 98 nodes · 126 edges · 18 communities (8 shown, 10 thin omitted)
- Extraction: 94% EXTRACTED · 6% INFERRED · 0% AMBIGUOUS · INFERRED: 8 edges (avg confidence: 0.79)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- 12-Factor Compliance & Docs
- Spring Boot Application
- ArchUnit Architecture Tests
- Flyway Configuration
- Maven Wrapper Script
- Testcontainers Integration Tests
- Database & Dev/Prod Parity
- OpenCode Plugin
- Graphify Plugin
- Factor I: Codebase
- Factor XII: Admin Processes
- Factor III: Config
- Project README
- Architecture Tests Documentation
- Factor V: Build Release Run
- Factor VIII: Concurrency
- Maven Package
- Serena Config

## God Nodes (most connected - your core abstractions)
1. `TwelveFactorArchitectureTest` - 11 edges
2. `12-Factor App Golden Rules` - 7 edges
3. `FlywayMultiSchemaConfigTest` - 6 edges
4. `TwelveFactorArchitectureTest` - 6 edges
5. `FlywayMultiSchemaConfig` - 5 edges
6. `PortBindingAndDisposabilityTest` - 5 edges
7. `PortsAndAdaptersJavaApplicationTests` - 4 edges
8. `PortBindingAndDisposabilityTest` - 4 edges
9. `PortsAndAdaptersJavaApplication` - 3 edges
10. `FlywayProperties` - 3 edges

## Surprising Connections (you probably didn't know these)
- `H2 In-Memory Database` --semantically_similar_to--> `Factor X: Dev/Prod Parity`  [INFERRED] [semantically similar]
  src/main/resources/application-local.yaml → docs/twelve-factor-app.md
- `AGENTS Twelve-Factor Rule` --references--> `12-Factor App Golden Rules`  [EXTRACTED]
  AGENTS.md → docs/rules/twelve-factor.md
- `CLAUDE Twelve-Factor Rule` --references--> `12-Factor App Golden Rules`  [EXTRACTED]
  CLAUDE.md → docs/rules/twelve-factor.md
- `Spring Profiles System` --implements--> `Factor III: Config`  [INFERRED]
  src/main/resources/application.yaml → docs/twelve-factor-app.md
- `Audit Twelve-Factor Skill` --references--> `12-Factor App Golden Rules`  [EXTRACTED]
  .opencode/skills/audit-twelve-factor/SKILL.md → docs/rules/twelve-factor.md

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **12-Factor Automation Cluster** — opencode_skills_audit-twelve-factor_audit_skill, docs_tests-arquitectura_twelve_factor_architecture_test, docs_tests-arquitectura_port_binding_disposability_test, docs_rules_twelve-factor_twelve_factor_rules [INFERRED 0.85]
- **Template Technology Stack** — docs_ports-and-adapters-java-template_spring_boot, docs_ports-and-adapters-java-template_postgres, docs_ports-and-adapters-java-template_resilience4j, docs_ports-and-adapters-java-template_flyway, docs_ports-and-adapters-java-template_hexagonal_architecture [EXTRACTED 0.95]
- **Spring Configuration Profiles** — src_main_resources_spring_profiles, src_main_resources_application_local_h2_database, docker-compose_postgres_service [INFERRED 0.75]

## Communities (18 total, 10 thin omitted)

### Community 0 - "12-Factor Compliance & Docs"
Cohesion: 0.12
Nodes (19): AGENTS Twelve-Factor Rule, CLAUDE Twelve-Factor Rule, Factor II: Dependencies Compliance, Design Notes, Hexagonal Architecture, Resilience4j, Spring Boot, Template Overview (+11 more)

### Community 1 - "Spring Boot Application"
Cohesion: 0.23
Nodes (8): javax.sql.DataSource, org.springframework.boot.autoconfigure.SpringBootApplication, org.springframework.boot.test.context.SpringBootTest, org.springframework.test.context.ActiveProfiles, PortsAndAdaptersJavaApplication, FlywayMultiSchemaConfigTest, TestApp, PortsAndAdaptersJavaApplicationTests

### Community 2 - "ArchUnit Architecture Tests"
Cohesion: 0.28
Nodes (3): com.tngtech.archunit.core.domain.JavaClasses, org.junit.jupiter.api.Test, TwelveFactorArchitectureTest

### Community 3 - "Flyway Configuration"
Cohesion: 0.29
Nodes (8): org.springframework.boot.CommandLineRunner, org.springframework.boot.context.properties.ConfigurationProperties, org.springframework.boot.context.properties.EnableConfigurationProperties, org.springframework.context.annotation.Bean, org.springframework.context.annotation.Configuration, org.springframework.context.annotation.Profile, FlywayMultiSchemaConfig, FlywayProperties

### Community 4 - "Maven Wrapper Script"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 5 - "Testcontainers Integration Tests"
Cohesion: 0.39
Nodes (4): GenericContainer, org.testcontainers.containers.GenericContainer, org.testcontainers.junit.jupiter.Testcontainers, PortBindingAndDisposabilityTest

### Community 6 - "Database & Dev/Prod Parity"
Cohesion: 0.50
Nodes (4): PostgreSQL Service, PostgreSQL, Factor X: Dev/Prod Parity, H2 In-Memory Database

### Community 7 - "OpenCode Plugin"
Cohesion: 0.50
Nodes (3): plugin, $schema, .opencode/plugins/graphify.js

## Knowledge Gaps
- **25 isolated node(s):** `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java`, `Factor I: Codebase Compliance`, `Factor II: Dependencies Compliance` (+20 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **10 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What connects `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java` to the rest of the system?**
  _25 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `12-Factor Compliance & Docs` be split into smaller, more focused modules?**
  _Cohesion score 0.12280701754385964 - nodes in this community are weakly interconnected._