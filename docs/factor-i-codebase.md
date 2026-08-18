# Factor I: Codebase — Análisis de cumplimiento

**Veredicto: CUMPLE**

## Qué pide el factor

- Un único codebase rastreado en control de versiones.
- Relación uno-a-uno entre codebase y app.
- Múltiples deploys (dev, staging, prod) se generan desde el mismo codebase.

## Qué se verificó

| Criterio | Resultado |
| ---------- | ----------- |
| Un solo repo Git | Sí — `ports-and-adapters-java` |
| Una sola app desplegable | Sí — `PortsAndAdaptersJavaApplication` |
| Código compartido entre apps distintas | No hay — no se aplica |
| Código duplicado o embebido de otros repos | No hay |

## Conclusión

El repo mantiene una relación uno-a-uno entre codebase y app. No hay código duplicado ni múltiples aplicaciones empaquetadas desde el mismo repo. No se requiere extraer dependencias compartidas porque no existen.
