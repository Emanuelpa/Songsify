# Songsify

API REST y aplicación web para publicar y descubrir recomendaciones de canciones y álbumes.

## Stack técnico

**Backend**
- Java 21
- Spring Boot 3
- Spring Data JPA
- Base de datos H2 (en memoria)
- Bean Validation
- Lombok
- Maven
- JUnit 5 + Mockito (tests unitarios)

**Frontend**
- Angular 18
- TypeScript
- Reactive Forms
- RxJS

## Arquitectura

**Backend** — arquitectura en capas, con DTOs separando el contrato de la API del modelo de persistencia:

```
com.emanuel.songsify/
├── controller/     → expone los endpoints REST
├── service/        → lógica de negocio y mapeo entre entidad y DTO
├── repository/     → acceso a datos (Spring Data JPA)
├── model/          → entidades JPA
├── dto/            → PostRequest (entrada) y PostResponse (salida)
├── exception/      → excepciones personalizadas y manejo global de errores
└── config/         → configuración (CORS)
```

**Frontend** — organizado por responsabilidad:

```
src/app/
├── models/         → interfaces TypeScript
├── services/       → comunicación con la API
├── components/     → post-list, post-item, post-form, post-detail
└── utils/          → helpers compartidos (manejo de errores)
```

## Requisitos previos

- Java 21 (JDK)
- Node.js 20 y npm
- Angular CLI 18 (opcional — el proyecto ya lo incluye como dependencia; si no lo tienes instalado globalmente, usa `npx ng` en lugar de `ng` en los comandos siguientes)

No necesitas instalar Maven por separado: el proyecto incluye el wrapper `./mvnw`.

## Cómo ejecutar el backend

```bash
cd songsify-backend
./mvnw spring-boot:run
```

El servidor arranca en `http://localhost:8080`.

La base de datos H2 es en memoria: se recrea vacía cada vez que se reinicia la aplicación. Puedes consultarla en `http://localhost:8080/h2-console` mientras el backend está corriendo (URL de conexión: `jdbc:h2:mem:songsify`, usuario: `root`, sin contraseña).

### Correr los tests

```bash
./mvnw test
```

## Cómo ejecutar el frontend

En otra terminal:

```bash
cd songsify-frontend
npm install
ng serve
```

Si no tienes Angular CLI instalado globalmente:

```bash
npx ng serve
```

La aplicación queda disponible en `http://localhost:4200`.

**Importante:** el backend debe estar corriendo antes de usar el frontend, ya que Angular consume la API en `http://localhost:8080`.

## Ejecutar con Docker (alternativa)

Si tienes Docker y Docker Compose instalados, puedes levantar ambos proyectos con un solo comando, sin instalar Java, Node ni Angular CLI localmente:

```bash
docker-compose up --build
```

- Backend disponible en `http://localhost:8080`
- Frontend disponible en `http://localhost:4200`

Para detener los contenedores:

```bash
docker-compose down
```

## Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET    | `/posts/` | Lista todas las recomendaciones |
| GET    | `/posts/{id}` | Obtiene una recomendación por id |
| POST   | `/posts/` | Crea una nueva recomendación |
| PUT    | `/posts/{id}` | Actualiza una recomendación existente |
| DELETE | `/posts/{id}` | Elimina una recomendación |

**Ejemplo de body para crear/actualizar (POST/PUT):**

```json
{
  "title": "Por qué OK Computer sigue siendo relevante",
  "author": "Emanuel",
  "songName": "Paranoid Android",
  "singerName": "Radiohead",
  "songUrl": "https://open.spotify.com/track/6LgJvl0Xdtc73RJ1mmpotq",
  "description": "Un álbum que anticipó la ansiedad tecnológica del siglo XXI."
}
```

## Funcionalidades

- CRUD completo de recomendaciones (crear, listar, ver detalle, editar, eliminar)
- Validación de campos obligatorios en backend (Bean Validation) y frontend (Reactive Forms), con mensajes específicos por campo
- Confirmación antes de eliminar una recomendación
- Manejo de estados de carga y error en la interfaz
- Tests unitarios del servicio principal con JUnit 5 y Mockito