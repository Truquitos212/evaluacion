# GreenBite

Proyecto evaluativo de Arquitectura de Software: plataforma de suscripción de alimentos orgánicos.

GreenBite permite a los usuarios ver un catálogo de cajas, agregar productos al carrito y confirmar pedidos semanales. El proyecto está organizado como una arquitectura de microservicios con un frontend React y tres módulos Java Spring Boot.

---

## Estructura del proyecto

- `frontend/` - aplicación web React que consume el BFF.
- `bff-service/` - Backend-for-Frontend que unifica las llamadas desde el frontend a los microservicios.
- `producto-service/` - microservicio de catálogo que expone los productos disponibles.
- `compra-service/` - microservicio de compras que gestiona la suscripción y el registro de pedidos.

---

## Tecnologías principales

- Java 17
- Spring Boot 3.2
- Spring Web
- Spring Data JPA
- H2 en memoria
- Maven
- React 18
- Node.js

---

## Módulos y responsabilidades

### `frontend/`

- Interfaz de usuario con React.
- Componentes principales:
  - `CatalogoCajas.jsx` - muestra el catálogo de cajas.
  - `Carrito.jsx` - gestiona el carrito y la confirmación de la compra.
- Consume los endpoints del BFF en `http://localhost:8080`.

### `bff-service/`

- Expone la API unificada para el frontend.
- Reenvía peticiones a `producto-service` y `compra-service`.
- Endpoints principales:
  - `GET /api/productos`
  - `GET /api/productos/{id}`
  - `POST /api/compras`

### `producto-service/`

- Gestiona el catálogo de productos.
- Proporciona controladores y repositorios para los productos.
- Se ejecuta en `http://localhost:8081`.

### `compra-service/`

- Gestiona compras y totalización.
- Persiste compras usando H2 en memoria para pruebas.
- Se ejecuta en `http://localhost:8082`.

---

## Puertos recomendados

- `frontend` → `http://localhost:3000`
- `bff-service` → `http://localhost:8080`
- `producto-service` → `http://localhost:8081`
- `compra-service` → `http://localhost:8082`

---

## Requisitos previos

- Java 17+
- Maven 3.8+
- Node.js 18+
- npm o yarn

---

## Cómo ejecutar el proyecto

Ejecuta cada módulo en su propia terminal:

1. `producto-service`

```bash
cd producto-service
mvn spring-boot:run
```

2. `compra-service`

```bash
cd compra-service
mvn spring-boot:run
```

3. `bff-service`

```bash
cd bff-service
mvn spring-boot:run
```

4. `frontend`

```bash
cd frontend
npm install
npm start
```

Abre la app en el navegador:

```bash
http://localhost:3000
```

---

## Cómo ejecutar pruebas

### Pruebas del backend

Desde cada módulo Java:

```bash
cd bff-service
mvn clean test
```

```bash
cd producto-service
mvn clean test
```

```bash
cd compra-service
mvn clean test
```

### Pruebas del frontend

```bash
cd frontend
npm test
```

---

## Notas útiles

- El BFF actúa como fachada para simplificar la integración del frontend con los microservicios.
- El proyecto usa H2 en memoria para datos de prueba, por lo que los datos se reinician al detener los servicios.
- Si las variables de puerto cambian, ajusta las URLs de los servicios en los archivos `application.properties` y el cliente del BFF.

---

## Contacto

Cualquier mejora debe considerar:

- validar formularios en el frontend
- controlar errores de red en el BFF
- agregar persistencia real en `compra-service`
- documentar la API con Swagger o OpenAPI
