# Graph Report - ports-and-adapters-java  (2026-08-17)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 23 nodes · 27 edges · 5 communities (2 shown, 3 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `614c516d`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- mvnw
- PortsAndAdaptersJavaApplicationTests.java
- PortsAndAdaptersJavaApplication
- graphify.js
- com.example:ports-and-adapters-java

## God Nodes (most connected - your core abstractions)
1. `PortsAndAdaptersJavaApplicationTests` - 3 edges
2. `PortsAndAdaptersJavaApplication` - 3 edges
3. `IMPORTANT: keep the reminder string free of backticks and $(...) constructs.` - 1 edges
4. `com.example:ports-and-adapters-java` - 0 edges

## Surprising Connections (you probably didn't know these)
- None detected - all connections are within the same source files.

## Import Cycles
- None detected.

## Communities (5 total, 3 thin omitted)

### Community 0 - "mvnw"
Cohesion: 0.33
Nodes (6): mvnw script, clean(), die(), exec_maven(), set_java_home(), verbose()

### Community 1 - "PortsAndAdaptersJavaApplicationTests.java"
Cohesion: 0.60
Nodes (3): org.junit.jupiter.api.Test, org.springframework.boot.test.context.SpringBootTest, PortsAndAdaptersJavaApplicationTests

## Knowledge Gaps
- **1 isolated node(s):** `com.example:ports-and-adapters-java`
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **What connects `com.example:ports-and-adapters-java` to the rest of the system?**
  _1 weakly-connected nodes found - possible documentation gaps or missing edges._