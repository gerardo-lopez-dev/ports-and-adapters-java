# Tests de Arquitectura

Dos tests automatizados verifican reglas de 12-Factor App que se pueden validar con análisis estático y ejecución real.

---

## 1. `TwelveFactorArchitectureTest` — Análisis estático con ArchUnit

Importa el bytecode de todas las clases (excepto test) y verifica reglas a nivel de paquetes y dependencias.

| Factor verificado | Qué chequea |
|---|---|
| **II. Dependencies** | Ninguna clase usa `ProcessBuilder` ni `Runtime.exec()`. Las dependencias se declaran en `pom.xml`, no se invocan binarios del SO. |
| **IV. Backing services** | El paquete `domain` no depende de `infrastructure`, ni de `org.springframework.*`, ni de `org.springframework.data.*`. Solo accede a servicios externos a través de puertos (interfaces). |
| **VI. Processes** | Ninguna clase tiene campos `static` mutables (sin `final`). Estado compartido = estado en backing services, no en memoria del proceso. |
| **XI. Logs** | No se usa `System.out`, `FileWriter` ni `FileOutputStream`. Los logs van a stdout vía SLF4J/Logback. |

### Qué no puede verificar (factores I, III, V, VIII, X, XII)

Son reglas de proceso o de infraestructura que requieren revisión humana o checks de CI/CD. Se documentan en `docs/rules/twelve-factor.md` con checkboxes.

---

## 2. `PortBindingAndDisposabilityTest` — Ejecución real con Testcontainers

Construye la imagen Docker del proyecto, levanta un contenedor y mide comportamiento en runtime. No analiza bytecode; verifica que la app funciona de verdad como 12-Factor.

| Factor verificado | Qué chequea |
|---|---|
| **VII. Port binding** | La app arranca y expone su propio puerto (8080) sin servidor externo. El contenedor debe estar listening dentro de 15 segundos. |
| **IX. Disposability** | Al recibir SIGTERM (vía `container.stop()`), la app se apaga en menos de 5 segundos liberando recursos correctamente. |

### Por qué Testcontainers

- Levanta la app real (no un mock), usando el `Dockerfile` del proyecto.
- Valida startup time y graceful shutdown — cosas que ArchUnit no puede ver.
- Se integra con CI sin dependencias externas (Docker corre en el pipeline).

---

## Cubertura combinada

| Factor | Test |
|---|---|
| I. Codebase | Manual (revisión de repo) |
| II. Dependencies | `TwelveFactorArchitectureTest` |
| III. Config | Manual (revisión de `application.yaml`) |
| IV. Backing services | `TwelveFactorArchitectureTest` |
| V. Build/release/run | CI/CD pipeline |
| VI. Processes | `TwelveFactorArchitectureTest` |
| VII. Port binding | `PortBindingAndDisposabilityTest` |
| VIII. Concurrency | Manual (diseño) |
| IX. Disposability | `PortBindingAndDisposabilityTest` |
| X. Dev/prod parity | Manual (configuración) |
| XI. Logs | `TwelveFactorArchitectureTest` |
| XII. Admin processes | Manual (revisión de código) |
