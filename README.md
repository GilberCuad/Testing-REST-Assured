# 🧪 API Test Automation — REST Assured + Cucumber

Proyecto de automatización de pruebas para APIs REST, construido con **Java**, **Maven**, **REST Assured** y **Cucumber (BDD)**.

---

## 🛠️ Tecnologías utilizadas

| Herramienta     | Versión recomendada | Descripción                              |
|-----------------|---------------------|------------------------------------------|
| Java            | 11+                 | Lenguaje principal                       |
| Maven           | 3.8+                | Gestión de dependencias y build          |
| REST Assured    | 5.x                 | Librería para pruebas de APIs REST       |
| Cucumber        | 7.x                 | Framework BDD para escritura de escenarios |
| JUnit / TestNG  | 5.x / 7.x           | Runner de pruebas                        |

---

## 📁 Estructura del proyecto

```
src
├── test
│   ├── java
│   │   ├── runner
│   │   │   └── TestRunner.java        # Configuración del runner de Cucumber
│   │   ├── steps
│   │   │   └── ApiSteps.java          # Step definitions de los escenarios
│   │   └── utils
│   │       └── RequestManager.java    # Utilidades para configurar requests
│   └── resources
│       └── features
│           └── api.feature            # Escenarios escritos en Gherkin
pom.xml
```

---

## ⚙️ Configuración — `pom.xml`

Dependencias principales necesarias en el proyecto:

```xml
<!-- REST Assured -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.0</version>
    <scope>test</scope>
</dependency>

<!-- Cucumber Java -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>

<!-- Cucumber JUnit -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>
```

---

## ▶️ Ejecución de pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar por tag de Cucumber
mvn test -Dcucumber.filter.tags="@smoke"

# Generar reporte
mvn verify
```

---

## 📊 Reportes

Los reportes se generan en `target/cucumber-reports/` tras ejecutar `mvn verify`. Se puede integrar con plugins como **Cucumber Reports** o **Extent Reports** para visualizaciones más detalladas.

---

## 📌 Notas

- La URL base de la API puede configurarse como variable de entorno o en un archivo `config.properties` dentro de `src/test/resources/`.
- Se recomienda usar `@Before` y `@After` hooks en Cucumber para setup y teardown de cada escenario.
- REST Assured permite validar headers, cookies, tiempos de respuesta y esquemas JSON de forma nativa.

---

> Proyecto desarrollado con fines de aprendizaje y práctica en automatización de pruebas de APIs.
