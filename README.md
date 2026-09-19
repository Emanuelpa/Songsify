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

**Frontend**
- Angular 18
- TypeScript
- RxJS

## Arquitectura

**Backend** — arquitectura en capas:

```
com.emanuel.songsify/
├── controller/     → expone los endpoints REST
├── service/        → lógica de negocio
├── repository/     → acceso a datos (Spring Data JPA)
├── model/          → entidades JPA
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

- Java 21
- Maven (o usar el wrapper `./mvnw` incluido, no requiere instalación aparte)
- Node.js 20 y npm
- Angular CLI 18 (`npm install -g @angular/cli@18`)

## Cómo ejecutar el backend

```bash
cd songsify-backend
./mvnw spring-boot:run
```

El servidor arranca en `http://localhost:8080`.

La base de datos H2 es en memoria: se recrea vacía cada vez que se reinicia la aplicación. Puedes consultarla en `http://localhost:8080/h2-console` mientras el backend está corriendo (URL de conexión: `jdbc:h2:mem:songsify`, usuario: `root`, sin contraseña).

## Cómo ejecutar el frontend

En otra terminal:

```bash
cd songsify-frontend
npm install
ng serve
```

La aplicación queda disponible en `http://localhost:4200`.

**Importante:** el backend debe estar corriendo antes de usar el frontend, ya que Angular consume la API en `http://localhost:8080`.

## Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET    | `/posts/` | Lista todas las recomendaciones |
| GET    | `/posts/{id}` | Obtiene una recomendación por id |
| POST   | `/posts/` | Crea una nueva recomendación |
| PUT    | `/posts/{id}` | Actualiza una recomendación existente |
| DELETE | `/posts/{id}` | Elimina una recomendación |

## Funcionalidades

- CRUD completo de recomendaciones (crear, listar, ver detalle, editar, eliminar)
- Validación de campos obligatorios, con mensajes específicos por campo
- Confirmación antes de eliminar una recomendación
- Manejo de estados de carga y error en la interfaz