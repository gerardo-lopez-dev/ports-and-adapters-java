# The Twelve-Factor App

¡Hola! Qué excelente iniciativa la de querer dominar la metodología de **The Twelve-Factor App**. Como tu mentor técnico, me alegra mucho guiarte en este viaje por los pilares de las aplicaciones modernas, escalables y listas para la nube.

Esta metodología no es un conjunto de reglas teóricas y aburridas; es un mapa de supervivencia pragmático nacido de la experiencia real de equipos que desplegaban miles de aplicaciones en producción. Al adoptarla, verás que tu día a día como desarrollador pasa de ser una constante "extinción de incendios" a un proceso predecible, fluido y ágil.

Vamos a recorrer los doce factores uno por uno en su orden original, explicando de forma sencilla qué significan, qué dolor de cabeza nos evitan, cómo se aplican en el mundo real y cómo se entrelazan entre sí.

---

## 1. Codebase (Código base)

- **Traducción al español**: Código base.
- **Explicación simple**: Imagina que el código de tu aplicación es la receta maestra de un pastel. Este factor nos dice que esa receta debe guardarse en un único libro de cocina centralizado y compartido por todo el equipo (un repositorio de control de versiones como Git). Desde este único libro de cocina, puedes preparar el pastel en diferentes cocinas (entornos de desarrollo, pruebas, producción), pero la receta base siempre es la misma.
- **El problema que resuelve**: Cuando los equipos no siguen esta práctica, terminan con el código de la aplicación repartido en múltiples carpetas o repositorios sin un origen común, o cometen el error de usar una sola base de código para empaquetar y lanzar múltiples aplicaciones independientes (por ejemplo, usando scripts de inicio distintos). El dolor real es la **imposibilidad de automatizar** los despliegues de forma confiable, la falta de auditoría sobre qué versión exacta está corriendo en producción y la pesadilla de resolver conflictos de integración cuando varios equipos meten mano sin control.
- **Ejemplo concreto**: Un repositorio Git único para tu microservicio (siguiendo el patrón de un repositorio por microservicio o _multirepo_). El código se rastrea allí (por ejemplo, en GitHub o Gogs) y genera entregas inmutables (releases) para desarrollo, staging y producción. Si necesitas compartir código común (como utilidades de conexión a bases de datos), la metodología nos exige **no duplicar el código** ni meterlo a la fuerza en el mismo repositorio; en su lugar, debemos extraerlo a su propio repositorio y empaquetarlo como una biblioteca que las demás aplicaciones importen como una dependencia.
- **Relación con otros factores**: Se conecta con **Dependencies** (el código compartido se maneja como una dependencia declarada) y con **Config** (el mismo código base se despliega en múltiples entornos variando únicamente la configuración externa).

---

## 2. Dependencies (Dependencias)

- **Traducción al español**: Dependencias.
- **Explicación simple**: Tu aplicación rara vez trabaja sola; suele necesitar de herramientas y bibliotecas de código escritas por otros para funcionar. Este principio exige que declares de forma **explícita y clara** en un documento de texto cuáles son esas bibliotecas externas que tu aplicación necesita, y que las "aísles", asegurando que la aplicación lleve sus propias herramientas en lugar de asumir que el servidor donde se ejecute ya las tiene instaladas por defecto.
- **El problema que resuelve**: Confiar en los llamados "servidores mamá" (servidores configurados manualmente que proveen todo de forma global). Si no aíslas tus dependencias, tarde o temprano sufrirás el dolor de _"en mi máquina funciona, pero en producción no"_. Esto pasa cuando un desarrollador actualiza una biblioteca localmente a la versión X, pero el servidor de producción tiene la versión X-1 instalada de forma global. Este desfase provoca desde fallos catastróficos en tiempo de ejecución hasta sutiles corrupciones de datos muy difíciles de diagnosticar.
- **Ejemplo concreto**: En un proyecto de Node.js, declarar tus dependencias en el archivo `package.json`; en Java, usar un archivo de configuración como `pom.xml` de Maven o `build.gradle` de Gradle. Al desplegar, se utiliza un sistema como **Docker**. En el archivo de configuración `Dockerfile` de tu contenedor, declaras explícitamente la versión exacta del sistema operativo y entorno de ejecución (por ejemplo, `FROM node:18`), aislando completamente la aplicación de la configuración global de la máquina física.
- **Relación con otros factores**: Se relaciona con **Build, release, run** (las dependencias se descargan y empaquetan estrictamente durante la etapa de compilación/build) y **Dev/prod parity** (garantiza que desarrolladores y servidores de producción ejecuten exactamente las mismas versiones de las bibliotecas).

---

## 3. Config (Configuración)

- **Traducción al español**: Configuración.
- **Explicación simple**: La configuración es todo aquello que cambia dependiendo de dónde se ejecute tu aplicación (por ejemplo, las credenciales de la base de datos de pruebas frente a las de producción, o las URLs de servicios externos). Este factor exige que **separes completamente la configuración del código**. El código de tu aplicación debe ser agnóstico del entorno, y la configuración debe inyectarse desde fuera en el momento en que la aplicación arranca.
- **El problema que resuelve**: El error gravísimo (y sumamente común) de dejar contraseñas, tokens de API o configuraciones específicas de entornos "hardcodeadas" (escritas a mano) directamente en archivos dentro del código fuente de la aplicación (como archivos `.properties` o `.yml` empaquetados). Si el código es público o si el repositorio de Git se llega a filtrar en plataformas como GitHub, expones credenciales sumamente sensibles, lo que puede resultar en hackeos masivos o facturas de miles de dólares por el uso no autorizado de tus servicios en la nube.
- **Ejemplo concreto**: En lugar de leer credenciales de un archivo interno, tu código debe esperar recibir variables de entorno del sistema operativo. En Node.js las leerías con `process.env.DATABASE_URL`; en Go, mediante herramientas de configuración como `godotenv` o `Viper`. Al desplegar con **Docker Compose** o **Kubernetes**, inyectas estas variables de forma dinámica en tiempo de ejecución sin alterar el contenedor compilado:

```yaml
# Ejemplo en docker-compose.yml
services:
  mi-servicio:
    image: mi-app:latest
    environment:
      - DATABASE_URL=postgres://usuario:password@postgres:5432/inventario
```

- **Relación con otros factores**: Es clave para **Backing services** (las URLs de conexión a las bases de datos o colas se manejan como recursos configurables) y para **Build, release, run** (el mismo artefacto compilado se combina con diferentes configuraciones para crear entregas únicas por entorno).

---

## 4. Backing services (Servicios de respaldo)

- **Traducción al español**: Servicios de respaldo.
- **Explicación simple**: Un servicio de respaldo es cualquier servicio externo que tu aplicación necesita para hacer su trabajo (bases de datos, servidores de correo, colas de mensajería, sistemas de caché). La aplicación debe tratar a estos servicios como **recursos adjuntos**. Esto significa que a tu aplicación no le debe importar si la base de datos se ejecuta en el mismo servidor localmente o es un servicio de pago en la nube gestionado por un tercero; simplemente se conecta mediante una URL y credenciales inyectadas de forma externa.
- **El problema que resuelve**: El acoplamiento rígido de la aplicación a una base de datos o servidor de infraestructura específico. Si un servicio de respaldo falla o se degrada, una aplicación acoplada no tiene forma de recuperarse fácilmente. El dolor de los equipos es tener que recompilar y redeployar código únicamente para apuntar a un servidor de base de datos de respaldo en caso de una falla en el servidor principal.
- **Ejemplo concreto**: Si tu aplicación requiere almacenar datos, en tu código creas una interfaz o abstracción de persistencia (como un Repositorio). De este modo, puedes utilizar una base de datos ligera en memoria (como H2) para desarrollo local y pruebas rápidas, y cambiar a una base de datos relacional de nivel de producción (como PostgreSQL) en el servidor definitivo mediante una simple actualización en la URL de conexión en las variables de entorno, sin modificar una sola línea de código de negocio. También se pueden usar patrones de resiliencia como **Circuit Breakers** (cortocircuitos) para detectar si un servicio de respaldo está fallando y reaccionar de inmediato con una ruta segura.
- **Relación con otros factores**: Se apoya enteramente en el factor de **Config** (la conexión al recurso se define en las variables de entorno) y promueve la **Disposability** (un servicio de respaldo puede acoplarse y desacoplarse a voluntad sin interrumpir el ciclo de vida de la aplicación).

---

## 5. Build, release, run (Build, release, ejecución)

- **Traducción al español**: Compilación, entrega y ejecución.
- **Explicación simple**: Este factor exige una **separación estricta** entre las fases que recorre tu código antes de estar activo en producción:
  1. **Build (Compilación)**: Se toma el código del repositorio Git y se transforma en un paquete cerrado e inmutable (un binario, un archivo `.jar` o una imagen de Docker).
  2. **Release (Entrega)**: Se combina ese paquete inmutable de la fase anterior con la configuración específica del entorno de destino (por ejemplo, variables de producción) para crear un "release" con un identificador único.
  3. **Run (Ejecución)**: Se levanta y arranca el entorno de ejecución utilizando ese release inmutable.
- **El problema que resuelve**: El doloroso y peligroso hábito de hacer "parches rápidos", depurar o editar archivos de configuración directamente en los servidores de producción activos. Si modificas el código directamente en el servidor de producción, pierdes la trazabilidad, se vuelve imposible auditar qué versión exacta está corriendo y no hay forma segura de realizar un "rollback" (volver atrás) si algo sale mal.
- **Ejemplo concreto**: El flujo de un pipeline de integración y despliegue continuo (CI/CD):
  - **Build**: Al hacer commit en Git, la herramienta de CI (como GitHub Actions o Jenkins) compila tu aplicación de Node.js y empaqueta una imagen de Docker inmutable etiquetada con el ID del commit (ej. `mi-app:a1b2c3d`).
  - **Release**: La herramienta combina la imagen `mi-app:a1b2c3d` con el ConfigMap/Secret de producción en Kubernetes para generar un release único (ej. `release-v42`).
  - **Run**: El orquestador de Kubernetes descarga el `release-v42` y arranca los pods que ejecutarán el proceso de manera segura.
- **Relación con otros factores**: Depende de **Codebase** (las fuentes de donde nace la compilación), **Dependencies** (las herramientas que se necesitan durante el build), y habilita la **Dev/prod parity** (ya que el mismo artefacto compilado en el "build" se prueba en staging antes de enviarse a producción en el "release").

---

## 6. Processes (Procesos)

- **Traducción al español**: Procesos.
- **Explicación simple**: Tu aplicación debe ejecutarse como uno o más **procesos sin estado (stateless)**. Esto significa que el proceso que ejecuta tu código no debe guardar información en su propia memoria RAM o en su disco duro local con la expectativa de que estará ahí para la siguiente petición del usuario. Cualquier dato que necesite persistencia debe guardarse en un servicio de respaldo que sí maneje el estado de forma segura (como una base de datos).
- **El problema que resuelve**: La pérdida de datos y sesiones de los usuarios cuando un servidor se reinicia o cuando se escala la aplicación. Un dolor clásico es el de las "sesiones pegajosas" (_sticky sessions_), donde el usuario pierde su sesión de navegación o los productos agregados a su carrito de compras si la petición es redirigida a otro servidor de la red. Al no tener estado, no tienes que preocuparte de que los procesos individuales compartan memoria local.
- **Ejemplo concreto**: Si estás programando una API en Node.js, nunca guardes las sesiones de usuario en un arreglo en memoria dentro de tu código (como `let sessions = []`). En su lugar, debes delegar el almacenamiento de estas sesiones a una base de datos en caché externa y de alta velocidad como **Redis**. Así, si el proceso de tu aplicación se cae o se escala, cualquier otro proceso puede atender al usuario consultando la sesión en el Redis centralizado.
- **Relación con otros factores**: Se conecta con **Backing services** (depende de almacenes de datos externos para el estado) y es el prerrequisito para la **Concurrency** (si tus procesos no tienen estado, puedes añadir cientos de instancias idénticas para balancear la carga fácilmente).

---

## 7. Port binding (Vinculación de puertos)

- **Traducción al español**: Vinculación de puertos.
- **Explicación simple**: La aplicación debe ser completamente autónoma y auto-contenida. En lugar de depender de que un servidor web externo (como Apache o Tomcat) esté instalado globalmente en la máquina para inyectar y "hostear" tu código, la aplicación debe **exponer sus servicios directamente vinculándose a un puerto** de red (por ejemplo, el puerto 3000 o 8080) para escuchar peticiones entrantes.
- **El problema que resuelve**: El acoplamiento de tu código a contenedores de aplicaciones pesados e independientes que deben administrarse de forma manual. Si dependes de un Tomcat global en el servidor, no puedes ejecutar múltiples aplicaciones con tecnologías diferentes en el mismo servidor de manera aislada, ni puedes garantizar la portabilidad de tu aplicación de una máquina a otra.
- **Ejemplo concreto**: En una aplicación web en Node.js usando Express, tu código crea su propio servidor web interno y se vincula al puerto que le indica el entorno:

```javascript
const express = require("express");
const app = express();
// Lee el puerto desde las variables de entorno
const port = process.env.PORT || 3000;

app.get("/", (req, res) => res.send("¡Hola, mundo!"));
app.listen(port, () => console.log(`Escuchando en el puerto ${port}`));
```

Al empaquetar con Docker, expones este puerto directamente para que el balanceador de carga o el orquestador redirija el tráfico hacia él.

- **Relación con otros factores**: Se apoya en **Dependencies** (el servidor web es una biblioteca empaquetada como dependencia en el código) y en **Config** (el puerto de red se inyecta como una variable de entorno).

---

## 8. Concurrency (Concurrencia)

- **Traducción al español**: Concurrencia.
- **Explicación simple**: Cuando tu aplicación recibe demasiado tráfico y empieza a quedarse sin recursos, la solución tradicional es hacer la máquina más grande añadiendo más RAM y procesador (escalado vertical). Este principio exige que en su lugar **escales horizontalmente usando el modelo de procesos**. Es decir, en lugar de hacer un único proceso gigante, multiplicas la cantidad de procesos individuales y sin estado corriendo en paralelo, distribuyendo el tráfico entre ellos con un balanceador de carga.
- **El problema que resuelve**: Los límites físicos y los costos exorbitantes de hacer crecer un servidor físico de manera vertical. Además, resuelve la **falta de aislamiento de fallas** en arquitecturas monolíticas; si toda tu carga corre en un solo hilo o proceso grande, un simple error de desbordamiento de memoria o un error en una transacción tumbará el servicio para el 100% de tus usuarios.
- **Ejemplo concreto**: En lugar de configurar hilos complejos dentro del código de tu microservicio, dejas que la infraestructura maneje la concurrencia. En un balanceador de carga, distribuyes las peticiones entrantes entre múltiples contenedores clonados de tu microservicio. En **Kubernetes**, escalar horizontalmente es tan simple como indicarle al orquestador en tu archivo de configuración la cantidad de instancias concurrentes que deseas ejecutar en paralelo (replicas):

```yaml
# Fragmento de un deployment en Kubernetes
spec:
  replicas: 3 # Kubernetes mantendrá activas 3 instancias idénticas de tu app
```

- **Relación con otros factores**: Depende críticamente de que tus **Processes** sean completamente sin estado (stateless) para poder clonar instancias libremente sin corromper datos, y se beneficia de la **Disposability** para que las nuevas instancias arranquen y mueran rápido en respuesta a picos de tráfico.

---

## 9. Disposability (Desechabilidad)

- **Traducción al español**: Desechabilidad.
- **Explicación simple**: Tus procesos deben ser desechables; esto significa que deben ser capaces de **iniciarse rápidamente** (en segundos) y de **apagarse con elegancia** (graceful shutdown) de forma inmediata ante una señal de apagado, completando las tareas que tienen a medias y liberando sus conexiones sin colgarse.
- **El problema que resuelve**: Los procesos que tardan minutos en arrancar (por ejemplo, porque intentan pre-cargar bases de datos gigantescas en memoria local en el arranque) no sirven para responder de forma rápida ante ráfagas de tráfico inesperadas. Por otra parte, las aplicaciones que no manejan un apagado elegante dejan transacciones a medias, corrompen datos en las colas de mensajería y causan bloqueos de recursos que impiden la recuperación rápida del sistema tras un fallo.
- **Ejemplo concreto**: En tu código (por ejemplo, en Go o Node.js), debes capturar las señales de apagado del sistema operativo (como `SIGTERM`). Al capturar la señal, detienes la recepción de nuevas conexiones de red, permites un tiempo de gracia para terminar de procesar las solicitudes que ya están en curso, cierras las conexiones activas a la base de datos y finalizas el proceso con un código de salida limpio (0).
- **Relación con otros factores**: Se beneficia directamente de los **Processes** sin estado, promueve la **Concurrency** (permitiendo un autoescalado elástico inmediato) y mejora la **Dev/prod parity** al hacer que los entornos locales de los desarrolladores sean rápidos de encender y apagar.

---

## 10. Dev/prod parity (Paridad entre desarrollo y producción)

- **Traducción al español**: Paridad entre desarrollo y producción.
- **Explicación simple**: Este factor exige mantener los entornos de desarrollo, pruebas (staging) y producción **lo más idénticos posible**. Esto aplica en tres dimensiones: el tiempo (reducir los meses que toma llevar código de desarrollo a producción a solo minutos u horas), las personas (los desarrolladores participan activamente en observar y operar sus servicios), y las herramientas (usar los mismos servicios de respaldo en desarrollo que en producción).
- **El problema que resuelve**: La dolorosa e impredecible brecha tecnológica entre entornos. Un error clásico es que el desarrollador use una base de datos ligera "en memoria" (como H2 o SQLite) para programar localmente por comodidad, mientras que el servidor de producción utiliza una base de datos relacional robusta (como Oracle o PostgreSQL). Esto provoca fallos inesperados en producción debido a sutiles diferencias en la sintaxis SQL, capacidades de índices, tiempos de respuesta o compatibilidades de controladores que nunca se detectaron en desarrollo.
- **Ejemplo concreto**: Usar herramientas de contenedorización como **Docker Compose** en tu máquina de desarrollo local. Así, en lugar de instalar manualmente bases de datos globales, declaras en un archivo el servicio de respaldo exacto que se usará en producción, garantizando que todos en el equipo usen el mismo motor y versión:

```yaml
# docker-compose.yml para desarrollo local
services:
  db:
    image: postgres:15.2 # Misma imagen y versión exacta que corre en producción
    ports:
      - "5432:5432"
```

- **Relación con otros factores**: Se entrelaza estrechamente con **Backing services** (al exigir uniformidad en los servicios conectados) y con **Config** (ya que la diferencia entre entornos debe ser únicamente el valor inyectado en la configuración, no la tecnología del servicio de respaldo).

---

## 11. Logs (Registros)

- **Traducción al español**: Registros.
- **Explicación simple**: Tu aplicación nunca debe preocuparse por elegir dónde se guardarán sus archivos de registro (logs), ni cómo rotarlos o guardarlos en disco. Tu aplicación simplemente debe **tratar los registros como flujos de eventos** continuos y enviarlos directamente sin búfer a la salida estándar (`stdout` y `stderr`). La responsabilidad de capturar, redirigir y almacenar estos flujos de eventos recae enteramente en la infraestructura de ejecución (las herramientas de la nube o contenedores).
- **El problema que resuelve**: El desborde de espacio en disco en los servidores debido a archivos de log gigantescos que nadie rotó, y la pérdida absoluta de logs críticos cuando un servidor de la nube (que es por naturaleza efímero) se cae o es destruido, llevándose consigo los archivos de texto almacenados localmente en su disco virtual.
- **Ejemplo concreto**: En tu código de Node.js, simplemente usas `console.log()` o `console.error()`, o en Go usas funciones nativas de salida de texto. Al correr en producción dentro de un contenedor en **Kubernetes**, el orquestador captura automáticamente este flujo de texto `stdout`. Posteriormente, un agente recolector (como Fluentd, Logstash o la suite ELK) toma esa salida de texto de todos los contenedores y la envía a un almacén centralizado (como Elasticsearch o Splunk) para que los desarrolladores puedan buscar y analizar los logs de forma unificada.
- **Relación con otros factores**: Se relaciona con **Processes** (la naturaleza efímera y sin estado de los procesos exige que los logs no dependan del disco duro local) y **Dev/prod parity** (el desarrollador ve los logs directamente en su terminal de desarrollo en tiempo real, mientras producción los indexa de forma centralizada sin cambiar el código de la app).

---

## 12. Admin processes (Procesos administrativos)

- **Traducción al español**: Procesos administrativos.
- **Explicación simple**: Las tareas de administración o mantenimiento únicas (como ejecutar scripts de migración de bases de datos, arreglar registros corruptos o correr consolas interactivas) deben tratarse como **procesos únicos de una sola ejecución**. Estos scripts deben ejecutarse exactamente bajo el mismo entorno, usando la misma base de código, las mismas dependencias de software y la misma configuración que los procesos regulares de la aplicación.
- **El problema que resuelve**: El enorme peligro de desincronización de la base de datos o fallos inesperados de scripts de mantenimiento. Ocurre cuando los administradores corren manualmente scripts escritos de forma improvisada directamente en el servidor usando versiones de bases de datos, dependencias o variables de configuración obsoletas o diferentes a las que tiene la aplicación activa.
- **Ejemplo concreto**: Al actualizar tu aplicación, necesitas correr migraciones en la base de datos. No lo haces entrando al servidor a correr consultas SQL de forma manual. En su lugar, empaquetas tus scripts de migración (usando herramientas como Flyway, Liquibase o migraciones nativas de Django/TypeORM) dentro del mismo código de la aplicación. Al desplegar en **Kubernetes**, puedes definir una tarea única de una sola ejecución (un `Job` o un `InitContainer`) que corra tus migraciones usando la misma imagen de Docker compilada antes de que arranquen los contenedores principales de la aplicación.
- **Relación con otros factores**: Depende fuertemente de **Codebase** y **Dependencies** (se ejecuta desde el mismo código y con el mismo manifest/imagen), y de **Build, release, run** (el script se despliega utilizando el mismo proceso inmutable de release).

---

## Tabla Resumen: El sistema de los 12 Factores de un vistazo

| Factor                     | Qué resuelve                                                                           | Ejemplo típico                                                                              |
| :------------------------- | :------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------ |
| **I. Codebase**            | Caos en control de cambios y falta de automatización de despliegues.                   | Un único repositorio Git por microservicio (_multirepo_).                                   |
| **II. Dependencies**       | Errores de "en mi máquina funciona" y desajustes de versiones en producción.           | Dependencias explícitas en `package.json` o `build.gradle` aisladas en contenedores Docker. |
| **III. Config**            | Fuga de credenciales sensibles y necesidad de recompilar por cada entorno.             | Inyección de credenciales mediante variables de entorno del sistema operativo.              |
| **IV. Backing services**   | Acoplamiento rígido a la infraestructura y caídas en cascada.                          | URLs de bases de datos configurables externamente y uso de Circuit Breakers.                |
| **V. Build, release, run** | Modificaciones riesgosas en caliente y falta de auditoría en producción.               | Pipelines de CI/CD que generan imágenes de Docker inmutables y despliegan releases.         |
| **VI. Processes**          | Pérdida de sesiones de usuario y límites severos para escalar la aplicación.           | Aplicación web stateless que almacena sesiones de usuario en un clúster de Redis.           |
| **VII. Port binding**      | Dependencia de servidores web pesados administrados manualmente en el sistema.         | Aplicaciones con Express (Node) o Go que escuchan de forma nativa en el puerto `$PORT`.     |
| **VIII. Concurrency**      | Altos costos y límites físicos de escalado vertical, con fallas catastróficas.         | Multiplicar contenedores idénticos balanceando la carga en Kubernetes.                      |
| **IX. Disposability**      | Lentitud en autoescalado y pérdida de transacciones por apagados abruptos.             | Capturar la señal `SIGTERM` en el código para realizar un apagado elegante.                 |
| **X. Dev/prod parity**     | Errores no detectados en pruebas causados por diferencias tecnológicas entre entornos. | Uso de Docker Compose local con el mismo motor de base de datos que en producción.          |
| **XI. Logs**               | Pérdida de logs históricos y saturación de almacenamiento local en el servidor.        | Escribir logs directamente a `stdout` (consola) para que la infraestructura los indexe.     |
| **XII. Admin processes**   | Desincronización de bases de datos y scripts de soporte incompatibles.                 | Correr migraciones de bases de datos empaquetadas como un InitContainer en Kubernetes.      |
