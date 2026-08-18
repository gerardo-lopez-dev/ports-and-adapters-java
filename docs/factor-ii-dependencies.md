# Factor II: Dependencies — Análisis de cumplimiento

**Veredicto: CUMPLE**

## Qué pide el factor

- Declarar explícitamente todas las dependencias externas.
- Aislar las dependencias del sistema operativo subyacente.
- No depender de paquetes instalados globalmente en la máquina.

## Qué se verificó

| Criterio | Resultado |
| ---------- | ----------- |
| Declaración explícita de dependencias | Sí — `pom.xml:32-48` con scopes correctos |
| Aislamiento en contenedor | Sí — `Dockerfile:1-16` multi-stage con imagen explícita |
| Maven Wrapper incluido | Sí — `mvnw` asegura versión consistente de Maven |
| Paquetes del sistema como dependencias | No hay |

## Conclusión

Todas las dependencias están declaradas en `pom.xml` con scopes apropiados (compile, runtime, test). El `Dockerfile` usa una imagen base explícita (`eclipse-temurin:25-jdk`) y descarga todas las dependencias durante el build (`dependency:go-offline`), aislando completamente la aplicación del sistema operativo. El Maven Wrapper garantiza que todos los entornos usen la misma versión de la herramienta de build.
