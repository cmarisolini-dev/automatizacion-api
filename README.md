
# Automatización API CULM – Swagger PetStore

Framework de automatización de pruebas REST desarrollado con **Java 17, Maven, Serenity BDD, Cucumber y Screenplay**. Permite validar operaciones de registro, consulta, actualización, filtrado y eliminación de mascotas mediante la API pública Swagger PetStore.

**API:** [Swagger PetStore](https://petstore.swagger.io/v2)

**Contrato:** [Swagger JSON](https://petstore.swagger.io/v2/swagger.json)

---

## 🛠️ Tecnologías

| Tecnología | Versión |
|---|---|
| Java JDK | 17 |
| Serenity BDD | 3.1.20 |
| Serenity Cucumber | 3.1.20 |
| JUnit | 4.13.2 |
| Maven | 3.x |
| REST Assured | 4.3.3 |

## 📁 Estructura del proyecto

```text
src/
├── main/java/org.example/
│   ├── clients/
│   │   └── PetStoreClient.java
│   ├── models/
│   │   └── PetData.java
│   ├── questions/
│   │   ├── ResponseBody.java
│   │   └── ResponseCode.java
│   ├── tasks/
│   │   ├── AddPet.java
│   │   ├── GetPetById.java
│   │   ├── FindPetsByStatus.java
│   │   ├── UpdatePet.java
│   │   ├── UpdatePetWithForm.java
│   │   └── DeletePet.java
│   └── utils/
│       └── TestIds.java
│
└── test/
    ├── java/org.example/
    │   ├── StepDefinitions/
    │   │   └── PetStepDefinition.java
    │   ├── RunnerTest.java
    │   └── KnownDefectsIT.java
    │
    └── resources/
        ├── features/
        │   ├── 01_AddPet.feature
        │   ├── 02_GetPet.feature
        │   ├── 03_UpdatePet.feature
        │   ├── 04_FindPets.feature
        │   ├── 05_UpdatePetForm.feature
        │   └── 06_DeletePet.feature
        ├── known-defects/
        │   ├── 01_BadPostId.feature
        │   └── 02_BadPutId.feature
        └── logback-test.xml
```

## 🔌 Operaciones de la API

| Método | Endpoint | Operación |
|---|---|---|
| POST | `/pet` | Registrar mascota |
| GET | `/pet/{petId}` | Consultar mascota por ID |
| PUT | `/pet` | Actualizar mascota mediante JSON |
| GET | `/pet/findByStatus` | Filtrar mascotas por estado |
| POST | `/pet/{petId}` | Actualizar mascota mediante formulario |
| DELETE | `/pet/{petId}` | Eliminar mascota |

## 🧪 Escenarios de prueba

La suite de aceptación contiene **12 escenarios Gherkin y 20 ejecuciones**, distribuidos en 14 ejecuciones positivas y 6 negativas.

| Caso | Operación | Ejecuciones | Validación |
|---|---|---:|---|
| CP01 | POST: Registrar | 3 | HTTP 200, datos completos y persistencia |
| CP02 | POST: Registrar | 1 | HTTP 415, Content-Type no admitido |
| CP03 | GET: Consultar | 2 | HTTP 200 y coincidencia de datos |
| CP04 | GET: Consultar | 1 | HTTP 404 para ID inexistente |
| CP05 | PUT: Actualizar JSON | 2 | HTTP 200, nuevos datos y persistencia |
| CP06 | PUT: Actualizar JSON | 1 | HTTP 415, Content-Type no admitido |
| CP07 | GET: Filtrar | 3 | HTTP 200, estado correcto e inclusión de mascota propia |
| CP08 | GET: Filtrar | 1 | Rechazo del estado inválido o lista vacía |
| CP09 | POST: Actualizar formulario | 2 | HTTP 200 y persistencia |
| CP10 | POST: Actualizar formulario | 1 | HTTP 404 para ID inexistente |
| CP11 | DELETE: Eliminar | 2 | HTTP 200 y consulta posterior HTTP 404 |
| CP12 | DELETE: Eliminar | 1 | HTTP 404 para ID inexistente |

## 🏷️ Tags utilizados

| Tag | Propósito |
|---|---|
| `@PetStore` | Identifica las pruebas de PetStore |
| `@Post_Pet` | Registro de mascotas |
| `@Get_Pet` | Consulta por identificador |
| `@Put_Pet` | Actualización mediante JSON |
| `@Find_Pet` | Filtrado por estado |
| `@Form_Pet` | Actualización mediante formulario |
| `@Delete_Pet` | Eliminación de mascotas |
| `@HappyPath` | Escenarios positivos |
| `@Negative` | Escenarios negativos |

Los casos individuales se identifican mediante `@CP01` hasta `@CP12`.

## 🏗️ Arquitectura

El proyecto utiliza el patrón **Screenplay** para organizar las interacciones con la API.

| Componente | Responsabilidad |
|---|---|
| `clients` | Configura el actor y la URL base de PetStore |
| `models` | Representa los datos de las mascotas y construye el JSON |
| `tasks` | Ejecuta las operaciones HTTP |
| `questions` | Obtiene y consulta códigos y datos de las respuestas |
| `utils` | Genera identificadores únicos |
| `StepDefinitions` | Relaciona los pasos Gherkin con las acciones y validaciones |
| `features` | Define los escenarios de prueba con Gherkin |

### Flujo técnico

```text
Pet.feature
     ↓
PetStepDefinition.java
     ↓
Tasks
 ├── AddPet.java
 ├── GetPetById.java
 ├── FindPetsByStatus.java
 ├── UpdatePet.java
 ├── UpdatePetWithForm.java
 └── DeletePet.java
     ↓
Questions
 ├── ResponseCode.java
 └── ResponseBody.java
     ↓
Swagger PetStore API
```

## 📋 Criterios de prueba y datos

- Se generan identificadores aleatorios para los registros de prueba.
- Se comprueba la disponibilidad del identificador antes de crear una mascota.
- Cada escenario prepara sus propios datos, sin depender del orden de ejecución.
- Se validan los códigos HTTP, el tipo de contenido y los campos del JSON.
- Se comprueba la persistencia mediante consultas posteriores.
- Se realiza limpieza automática de los registros propios al finalizar cada escenario, cuando es posible.

> **Consideración:** La API utilizada es un servidor público de demostración. Sus respuestas pueden diferir del contrato. El caso CP08 admite HTTP 400 o HTTP 200 con una lista vacía como comportamiento alternativo; esta última respuesta no representa conformidad estricta con un rechazo del estado inválido.

## ▶️ Ejecución en Windows
**Requisitos:** JDK 17, Maven y conexión a Internet.

### 1. Abrir el proyecto

Abrir en IntelliJ IDEA la carpeta raíz del proyecto, donde se encuentra `pom.xml`.

### 2. Configurar RunnerTest

1. En la parte superior derecha de IntelliJ IDEA, hacer clic en **Current File**.
2. Seleccionar **Edit Configurations**.
3. Elegir **JUnit** y crear una nueva configuración.
4. Asignar el nombre `RunnerTest`.
5. En **Class**, seleccionar la clase `RunnerTest`.
6. Verificar que el **JRE** esté configurado con **JDK 17**.
7. Hacer clic en **Apply** y luego en **OK**.

> **Importante:** El proyecto debe ejecutarse con JDK 17. Una versión diferente puede generar errores de compatibilidad.

### 3. Ejecutar las pruebas

Una vez guardada la configuración, en la parte superior derecha aparecerá `RunnerTest` en lugar de `Current File`.

Hacer clic en el botón **Run (▶)** para iniciar la ejecución de los escenarios automatizados.

### 4. Generar el reporte Serenity

Una vez finalizada la ejecución de los escenarios:

1. Abrir la ventana **Maven**, ubicada en el lateral derecho de IntelliJ IDEA.
2. Hacer clic en **Execute Maven Goal** (icono de la consola).
3. Ingresar el siguiente comando:

   ```bash
   mvn serenity:aggregate
   ```

4. Presionar Enter para generar el reporte.

### 5. Consultar los resultados

El reporte generado se encuentra en:

```text
target/site/serenity/index.html
```

Abrir `index.html` en el navegador para visualizar los resultados de las pruebas.



## 🔎 Diagnóstico de defectos conocidos

Se dispone de una suite independiente en `src/test/resources/known-defects`, ejecutada mediante `KnownDefectsIT.java`.

Contiene dos escenarios de diagnóstico:

| Caso | Operación | Problema identificado |
|---|---|---|
| DEF_CP02 | POST `/pet` | Identificador no numérico en JSON |
| DEF_CP06 | PUT `/pet` | Identificador no numérico en JSON |

En la ejecución V1 del 23/11/2026, ambos escenarios devolvieron HTTP 500 en lugar de los códigos HTTP 400 o 405 esperados.

Estos casos se mantienen separados de las pruebas de aceptación y no se consideran aprobados ni corregidos en el servidor.

### Reproducir las incidencias

```bash
mvn clean verify "-Dit.test=KnownDefectsIT"
```

Si el servidor continúa devolviendo HTTP 500, el diagnóstico fallará correctamente al detectar el comportamiento inesperado.

> **Importante:** Respaldar el reporte de aceptación antes de ejecutar esta suite y volver a ejecutar `mvn clean verify` para regenerarlo después.

## 🧾 Verificación manual de Content-Type

El 23/11/2026 se verificaron manualmente mediante `curl` las operaciones POST y PUT enviando un cuerpo `invalid` con `Content-Type: text/plain`.

Ambas operaciones devolvieron HTTP 415.

Los escenarios automáticos CP02 y CP06 incorporan estas validaciones. Su ejecución automática V2 todavía requiere validación local.

Para mayor detalle, consultar `docs/INCIDENCIAS.md`.

---

**Proyecto:** Automatización API CULM  
**API de pruebas:** Swagger PetStore  
**Framework:** Serenity BDD + Cucumber + Screenplay

---

🌸✨ Developed by **Leydi Marisol Cruz Ulloa** 👩🏻‍💻💻🌸