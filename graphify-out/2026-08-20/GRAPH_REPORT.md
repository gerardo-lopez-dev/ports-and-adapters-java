# Graph Report - ports-and-adapters-java  (2026-08-20)

## Corpus Check
- 16 files · ~10,258 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 94 nodes · 90 edges · 16 communities (13 shown, 3 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0ba97c87`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- The Twelve-Factor App
- mvnw
- Las 12 reglas
- Template: ports-and-adapters-java
- org.junit.jupiter.api.Test
- Ports and Adapters - Java
- Factor I: Codebase — Análisis de cumplimiento
- Factor II: Dependencies — Análisis de cumplimiento
- opencode.json
- PortsAndAdaptersJavaApplication
- graphify.js
- com.example:ports-and-adapters-java
- PROMPT
- 12-Factor App — Golden Rules

## God Nodes (most connected - your core abstractions)
1. `The Twelve-Factor App` - 14 edges
2. `Las 12 reglas` - 13 edges
3. `Ports and Adapters - Java` - 5 edges
4. `Template: ports-and-adapters-java` - 5 edges
5. `12-Factor App — Golden Rules` - 5 edges
6. `PROMPT` - 4 edges
7. `Factor I: Codebase — Análisis de cumplimiento` - 4 edges
8. `Factor II: Dependencies — Análisis de cumplimiento` - 4 edges
9. `PortBindingAndDisposabilityTest` - 3 edges
10. `PortsAndAdaptersJavaApplication` - 3 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Import Cycles
- None detected.

## Communities (16 total, 3 thin omitted)

### Community 0 - "The Twelve-Factor App"
Cohesion: 0.13
Nodes (14): 10. Dev/prod parity (Paridad entre desarrollo y producción), 11. Logs (Registros), 12. Admin processes (Procesos administrativos), 1. Codebase (Código base), 2. Dependencies (Dependencias), 3. Config (Configuración), 4. Backing services (Servicios de respaldo), 5. Build, release, run (Build, release, ejecución) (+6 more)

### Community 1 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 2 - "Las 12 reglas"
Cohesion: 0.15
Nodes (13): I. Codebase, II. Dependencies, III. Config, IV. Backing services, IX. Disposability, Las 12 reglas, V. Build, release, run, VI. Processes (+5 more)

### Community 3 - "Template: ports-and-adapters-java"
Cohesion: 0.33
Nodes (5): Auditoría 12-factor (estado tras esta versión), Comandos, Estructura del proyecto, Notas de diseño, Template: ports-and-adapters-java

### Community 4 - "org.junit.jupiter.api.Test"
Cohesion: 0.33
Nodes (4): PortBindingAndDisposabilityTest, org.junit.jupiter.api.Test, org.springframework.boot.test.context.SpringBootTest, PortsAndAdaptersJavaApplicationTests

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

### Community 12 - "PROMPT"
Cohesion: 0.33
Nodes (5): Orden de ejecución, PROMPT, Prompt: Scaffolding de template hexagonal + 12-factor en proyecto Java, Qué quiero que generes, Reglas de estilo

### Community 13 - "12-Factor App — Golden Rules"
Cohesion: 0.40
Nodes (4): 12-Factor App — Golden Rules, Alcance, Checklist final, Excepciones

## Knowledge Gaps
- **48 isolated node(s):** `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java`, `Requirements`, `Run` (+43 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Las 12 reglas` connect `Las 12 reglas` to `12-Factor App — Golden Rules`?**
  _High betweenness centrality (0.029) - this node is a cross-community bridge._
- **Why does `12-Factor App — Golden Rules` connect `12-Factor App — Golden Rules` to `Las 12 reglas`?**
  _High betweenness centrality (0.014) - this node is a cross-community bridge._
- **What connects `$schema`, `.opencode/plugins/graphify.js`, `com.example:ports-and-adapters-java` to the rest of the system?**
  _48 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `The Twelve-Factor App` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._