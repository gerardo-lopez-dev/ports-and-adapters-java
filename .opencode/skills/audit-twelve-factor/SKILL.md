---
name: audit-twelve-factor
description: "Audit the project against 12-Factor App rules. Runs ArchUnit + Testcontainers and generates a report. Trigger: 'audit twelve-factor', 'twelve-factor check', 'verify 12-factor', 'check 12-factor', 'audit 12-factor'"
---

## Reference

Golden rules: `docs/rules/twelve-factor.md` — read before starting.

## What it checks

| Factor | Tool | Test |
|--------|------|------|
| II. Dependencies | ArchUnit | `TwelveFactorArchitectureTest.factor_ii_*` |
| IV. Backing services | ArchUnit | `TwelveFactorArchitectureTest.factor_iv_*` |
| VI. Processes | ArchUnit | `TwelveFactorArchitectureTest.factor_vi_*` |
| XI. Logs | ArchUnit | `TwelveFactorArchitectureTest.factor_xi_*` |
| VII. Port binding | Testcontainers | `PortBindingAndDisposabilityTest.app_must_bind_its_own_port` |
| IX. Disposability | Testcontainers | `PortBindingAndDisposabilityTest.app_must_shutdown_gracefully_on_sigterm` |

Factors I, III, V, VIII, X, XII are manual checks — refer to the golden rule.

## Execution

### 1. ArchUnit (always)

```bash
mvn test -Dtest=TwelveFactorArchitectureTest -pl .
```

### 2. Testcontainers (only if Docker is running)

```bash
mvn test -Dtest=PortBindingAndDisposabilityTest -pl .
```

Check Docker first: `docker info > /dev/null 2>&1`. If it fails, skip and report as "not executed (Docker unavailable)".

### 3. Report

Generate a table with the result of each factor:

```
## Twelve-Factor Audit Report

| Factor | Status | Detail |
|--------|--------|--------|
| I. Codebase | — | Manual: no duplicated code across repos |
| II. Dependencies | ✅/❌ | {ArchUnit result} |
| III. Config | — | Manual: no hardcoded values |
| IV. Backing services | ✅/❌ | {ArchUnit result} |
| V. Build/release/run | — | Manual: same artifact for all environments |
| VI. Processes | ✅/❌ | {ArchUnit result} |
| VII. Port binding | ✅/❌/⏭ | {Testcontainers result or "skipped"} |
| VIII. Concurrency | — | Manual: horizontally scalable design |
| IX. Disposability | ✅/❌/⏭ | {Testcontainers result or "skipped"} |
| X. Dev/prod parity | — | Manual: same backing services everywhere |
| XI. Logs | ✅/❌ | {ArchUnit result} |
| XII. Admin processes | — | Manual: admin tasks as separate processes |

**Verdict**: {PASS / FAIL / PARTIAL}
**Suggested action**: {one-line next step}
```

If there are failures, list each violation with file:line and the golden rule it violates.
