# 🧪 API Test Automation — REST Assured + Cucumber

Proyecto de automatización de pruebas para la API REST de práctica de **Blass Academy** (`https://api.blassacademy.com/`), construido con **Java**, **Maven**, **REST Assured** y **Cucumber (BDD)**.

---

## 🛠️ Tecnologías utilizadas

| Herramienta            | Versión | Descripción                                         |
|------------------------|---------|-----------------------------------------------------|
| Java                   | 17      | Lenguaje principal                                  |
| Maven                  | 3.8+    | Gestión de dependencias y build                     |
| REST Assured           | 6.0.0   | Librería para pruebas de APIs REST                  |
| Cucumber               | 7.34.3  | Framework BDD (`cucumber-bom`)                      |
| JUnit Platform / Suite | 6.0.3   | Runner de pruebas (`junit-bom`)                     |
| PicoContainer          | 7.34.3  | Inyección de dependencias entre clases de steps     |
| Jackson Databind       | 3.1.2   | Deserialización de respuestas JSON a modelos        |
| Log4j2                 | 2.25.4  | Logs de requests/responses                          |

> Las versiones de Cucumber y JUnit se gestionan **solo** desde sus BOM en `<dependencyManagement>`. No declares versiones sueltas de esos artefactos en `<dependencies>` para evitar conflictos.

---

## 📁 Estructura del proyecto

```
src/test
├── java
│   ├── runner
│   │   └── TestRunner.java            # Suite de JUnit Platform que ejecuta Cucumber
│   └── blass/apitesting
│       ├── models                     # Records para deserializar respuestas
│       │   ├── Animal.java
│       │   ├── Amo.java
│       │   ├── Producto.java
│       │   └── Critica.java
│       ├── steps
│       │   ├── World.java             # Steps genéricos + hooks (@Before / @After)
│       │   ├── AnimalSteps.java       # Validaciones específicas de animales
│       │   └── ProductoSteps.java     # Validaciones específicas de productos
│       └── utils
│           ├── Config.java            # Lectura de config.properties / -D
│           ├── JsonManager.java       # Parseo JSON → modelos (Jackson)
│           ├── Logs.java              # Logger + adjuntos de logs por escenario
│           └── RequestFilter.java     # Filtro REST Assured que loguea request/response
└── resources
    ├── config.properties              # base.url y ruta de payloads
    ├── log4j2.xml                     # Configuración de logs (carpeta logs/)
    ├── features                       # Escenarios en Gherkin
    │   ├── Animales.feature
    │   ├── Auth.feature
    │   ├── Estado.feature
    │   └── Producto.feature
    └── payloads                       # Cuerpos JSON de las peticiones
        ├── animal/
        ├── auth/
        └── producto/
```

---

## ⚙️ Configuración

`src/test/resources/config.properties`:

```properties
base.url=https://api.blassacademy.com/
path.payloads=src/test/resources/payloads
```

Cualquier propiedad se puede sobrescribir por línea de comandos, por ejemplo:

```bash
mvn test -Dbase.url=https://otro-ambiente.com/
```

---

## 🧩 Steps disponibles

Definidos en `World.java` (genéricos):

| Step                                                       | Uso                                            |
|------------------------------------------------------------|------------------------------------------------|
| `Se usa el base path "<ruta>"`                             | Ruta del endpoint (admite `{param}`)           |
| `Se usa el payload "<carpeta/archivo.json>"`               | Body tomado de `payloads/`                     |
| `Se usa el body:` + tabla `clave \| valor`                 | Body JSON armado desde la tabla (ideal en Scenario Outline) |
| `Se asigna el path param "<clave>"="<valor>"`              | Reemplaza `{clave}` en la ruta                 |
| `Se asigna los query params "<clave>"="<valor>"`           | Agrega query param                             |
| `Se usa el token del login`                                | Agrega `Authorization: Bearer <accessToken>`   |
| `Se envia el request con el metodo <GET\|POST\|PUT\|PATCH\|DELETE>` | Ejecuta la petición                   |
| `Se verifica que el status code sea <n>`                   | Valida el status code                          |
| `Se verifica que "<jsonPath>" == "<valor>"`                | Valida un campo de la respuesta                |
| `Se verifica longitud del "<jsonPath>" == "<n>"`           | Valida la longitud de un campo de texto        |

### Autenticación

`Se usa el token del login` hace login con `payloads/auth/login.json` la primera vez que se usa en la ejecución, guarda el `accessToken` y lo reutiliza en los siguientes escenarios (el token dura 1 hora). Úsalo en cualquier escenario cuyo endpoint requiera sesión:

```gherkin
Given Se usa el base path "auth/cambiar-clave"
And Se usa el token del login
And Se usa el payload "auth/cambiarClave.json"
When Se envia el request con el metodo POST
```

### Scenario Outline (un escenario, varios datos)

`Auth.feature` incluye un ejemplo: el mismo flujo de login se ejecuta una vez por cada fila de `Examples`, reemplazando `<usuario>`, `<clave>`, `<status>` y `<mensaje>`:

```gherkin
Scenario Outline: Login rechazado: <caso>
  Given Se usa el base path "auth/login"
  And Se usa el body:
    | username | <usuario> |
    | password | <clave>   |
  When Se envia el request con el metodo POST
  Then Se verifica que el status code sea <status>
  And Se verifica que "mensaje" == "<mensaje>"

  Examples:
    | caso             | usuario       | clave      | status | mensaje                   |
    | clave incorrecta | standard_user | clave_mala | 401    | Usuario/Clave incorrectas |
```

Específicos:

- `Se verifica que existan <n> animales` (`AnimalSteps`)
- `Se verifica que el animal tenga:` + tabla (`AnimalSteps`)
- `Se verifica que el producto tenga:` + tabla (`ProductoSteps`)

---

## 🏷️ Etiquetas (tags)

| Tag          | Dónde                          | Propósito                                             |
|--------------|--------------------------------|-------------------------------------------------------|
| `@animales`  | Feature Animales               | Ejecutar solo el módulo de animales                   |
| `@productos` | Feature Productos              | Ejecutar solo el módulo de productos                  |
| `@auth`      | Feature Auth                   | Ejecutar solo autenticación                           |
| `@estado`    | Feature Estado                 | Health check de la API                                |
| `@smoke`     | Escenarios críticos de lectura | Estado, login exitoso, listar/obtener animal, obtener producto |
| `@login`     | Escenarios de login            | Login exitoso + login inválido                        |
| `@negativo`  | Casos de error esperados       | Login con credenciales inválidas (401)                |

Las etiquetas de un `Feature` se heredan en todos sus escenarios.

---

## ▶️ Ejecución de pruebas

```bash
# Ejecutar todos los escenarios
mvn test

# Solo smoke
mvn test -Dcucumber.filter.tags="@smoke"

# Solo login (positivo y negativo)
mvn test -Dcucumber.filter.tags="@login"

# Combinaciones
mvn test -Dcucumber.filter.tags="@auth and not @negativo"
mvn test -Dcucumber.filter.tags="@animales or @productos"
```

El runner (`TestRunner.java`) no fija ningún tag, así que por defecto se ejecuta todo y el filtro se decide al lanzar Maven.

---

## 📊 Reportes y logs

- **Reporte HTML de Cucumber**: `target/cucumber-reports/cucumber.html`, generado en cada `mvn test`.
  Cada escenario incluye como adjuntos sus logs (`TRACE-Logs`, `DEBUG-Logs`, `INFO-Logs`) con el detalle de request y response.
  Para abrirlo: doble clic en el archivo desde el explorador, o por terminal:

  ```bash
  start target/cucumber-reports/cucumber.html      # Windows
  open target/cucumber-reports/cucumber.html       # macOS
  xdg-open target/cucumber-reports/cucumber.html   # Linux
  ```
- **Archivos de log** (`log4j2.xml`), en la carpeta `logs/`:
  - `informativeLogs.log`: nivel INFO o superior (resultado de cada escenario)
  - `debugLogs.log`: DEBUG o superior (request/response completos)
  - `traceLogs.log`: todo
- La carpeta `logs/` está en `.gitignore` porque se regenera en cada ejecución.

---

> Proyecto desarrollado con fines de aprendizaje y práctica en automatización de pruebas de APIs.
