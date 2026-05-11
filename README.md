# GreenBite — Plataforma de suscripcion de alimentos sustentables

Evaluacion Parcial 2 — Arquitectura de Software
Ingenieria en Informatica

---

## Descripcion del sistema

GreenBite es una plataforma B2C que permite a los usuarios suscribirse semanalmente
a cajas de alimentos organicos de productores locales. Este proyecto implementa
el sistema usando arquitectura de microservicios.

---

## Componentes

| Componente           | Tecnologia     | Puerto |
|----------------------|---------------|--------|
| frontend             | React 18      | 3000   |
| bff-service          | Spring Boot   | 8080   |
| producto-service     | Spring Boot   | 8081   |
| compra-service       | Spring Boot   | 8082   |

---

## Patrones de diseno implementados

- **Facade** — `BackendParaFrontendService` oculta la complejidad de los microservicios al frontend
- **Singleton** — Spring gestiona los servicios como instancias unicas (`@Service`)
- **Repository** — `ProductoRepository` y `CompraRepository` abstraen el acceso a datos
- **Builder** — `Compra.Builder` permite construir objetos de forma legible
- **Observer** — React con `useState` notifica cambios del carrito a los componentes

---

## Como ejecutar

**Requisitos:** Java 17, Maven 3.8+, Node.js 18+

```bash
# Terminal 1 — Productos (8081)
cd producto-service
mvn spring-boot:run

# Terminal 2 — Compras (8082)
cd compra-service
mvn spring-boot:run

# Terminal 3 — Backend Para Frontend (8080)
cd bff-service
mvn spring-boot:run

# Terminal 4 — Frontend (3000)
cd frontend
npm install
npm start
```

Abrir navegador en http://localhost:3000

---

## Repositorio

https://github.com/Truquitos212/evaluacion
