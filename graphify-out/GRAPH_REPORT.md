# Graph Report - ports-and-adapters-java  (2026-08-18)

## Corpus Check
- 11 files · ~7,837 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 64 nodes · 62 edges · 11 communities (8 shown, 3 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `9b4288ac`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- mvnw
- PortsAndAdaptersJavaApplicationTests.java
- PortsAndAdaptersJavaApplication
- graphify.js
- com.example:ports-and-adapters-java
- The Twelve-Factor App
- Template: ports-and-adapters-java
- Ports and Adapters - Java
- Factor I: Codebase — Análisis de cumplimiento
- Factor II: Dependencies — Análisis de cumplimiento
- opencode.json

## God Nodes (most connected - your core abstractions)
1. `The Twelve-Factor App` - 14 edges
2. `Ports and Adapters - Java` - 5 edges
3. `Template: ports-and-adapters-java` - 5 edges
4. `Factor I: Codebase — Análisis de cumplimiento` - 4 edges
5. `Factor II: Dependencies — Análisis de cumplimiento` - 4 edges
6. `PortsAndAdaptersJavaApplication` - 3 edges
7. `PortsAndAdaptersJavaApplicationTests` - 3 edges
8. `plugin` - 2 edges
9. `$schema` - 1 edges
10. `.opencode/plugins/graphify.js` - 1 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Import Cycles
- None detected.

## Communities (11 total, 3 thin omitted)

### Community 0 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 1 - "PortsAndAdaptersJavaApplicationTests.java"
Cohesion: 0.60
Nodes (3): org.junit.jupiter.api.Test, org.springframework.boot.test.context.SpringBootTest, PortsAndAdaptersJavaApplicationTests

### Community 5 - "The Twelve-Factor App"
Cohesion: 0.13
Nodes (14): 10. Dev/prod parity (Paridad entre desarrollo y producción), 11. Logs (Registros), 12. Admin processes (Procesos administrativos), 1. Codebase (Código base), 2. Dependencies (Dependencias), 3. Config (Configuración), 4. Backing services (Servicios de respaldo), 5. Build, release, run (Build, release, ejecución) (+6 more)

### Community 6 - "Template: ports-and-adapters-java"
Cohesion: 0.33
Nodes (5): Auditoría 12-factor (estado tras esta versión), Comandos, Estructura del proyecto, Notas de diseño, Template: ports-and-adapters-java

### Community 7 - "Ports and Adapters - Java"
Cohesion: 0.33
Nodes (5): License, Ports and Adapters - Java, Requirements, Run, Tests

### Community 8 - "Factor I: Codebase — Análisis de cumplimiento"
Cohesion: 0.40
Nodes (4): Conclusión, Factor I: Codebase — Análisis de cumplimiento, Qué pide el factor, Qué se verificó

### Community 9 - "Factor II: Dependencies — Análisis de cumplimiento"
Cohesion: 0.40
Nodes (4): Conclusión, Factor II: Dependencies — Análisis de cumplimiento, Qué pide el factor, Qué se verificó

### Community 10 - "opencode.json"
Cohesion: 0.50
Nodes (3): plugin, $schema, .opencode/plugins/graphify.js

## Knowledge Gaps
- **30 isolated node(s):** `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java`, `Requirements`, `Run` (+25 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What connects `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java` to the rest of the system?**
  _30 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `The Twelve-Factor App` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._