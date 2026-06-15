# GreenBite - Resumen del Proyecto

Este documento resume el proyecto **GreenBite**, una plataforma de suscripción de alimentos orgánicos construida con microservicios Java Spring Boot y un frontend React.

---

## 1. Estructura del proyecto

```
evaluacion-main/
├─ README.md
├─ bff-service/
│  ├─ pom.xml
│  └─ src/main/java/com/greenbite/bff/
│     ├─ BackendParaFrontendApplication.java
│     ├─ controller/BackendParaFrontendControlador.java
│     ├─ dto/CompraDTO.java
│     ├─ dto/ProductoDTO.java
│     └─ service/BackendParaFrontendService.java
├─ compra-service/
│  ├─ pom.xml
│  ├─ src/main/java/com/greenbite/compras/
│  │  ├─ CompraServiceApplication.java
│  │  ├─ controlador/CompraControlador.java
│  │  ├─ modelo/Compra.java
│  │  ├─ modelo/ItemCompra.java
│  │  ├─ repositorio/CompraRepository.java
│  │  └─ servicio/CompraServicio.java
│  └─ src/main/resources/application.properties
├─ frontend/
│  ├─ package.json
│  ├─ public/index.html
│  └─ src/
│     ├─ App.css
│     ├─ App.js
│     ├─ index.js
│     └─ components/
│        ├─ Carrito.jsx
│        └─ CatalogoCajas.jsx
├─ producto-service/
│  ├─ pom.xml
│  ├─ src/main/java/com/greenbite/productos/
│  │  ├─ ProductoServiceApplication.java
│  │  ├─ config/CargadorDatos.java
│  │  ├─ controlador/ProductoControlador.java
│  │  ├─ modelo/Producto.java
│  │  ├─ repositorio/ProductoRepository.java
│  │  └─ servicio/ProductoServicio.java
│  └─ src/main/resources/application.properties
```

---

## 2. Visión general

GreenBite está diseñado como una aplicación de comercio electrónico por suscripción con tres componentes principales:

- `frontend/`: Interfaz web React para mostrar cajas y gestionar el carrito.
- `producto-service/`: Microservicio que gestiona el catálogo de cajas orgánicas.
- `compra-service/`: Microservicio que gestiona compras y suscripciones.
- `bff-service/`: Backend-para-Frontend que unifica las llamadas del frontend y oculta los microservicios.

Esta arquitectura separa la presentación, la lógica de catálogo y la lógica de compras. El BFF actúa como fachada para simplificar la integración.

---

## 3. Tecnologías usadas

- Java 17
- Spring Boot 3.2
- Spring Web
- Spring WebFlux (BFF)
- Spring Data JPA
- H2 en memoria
- React 18
- react-scripts
- Maven
- Lombok (opcional, declarado en `pom.xml`)

---

## 4. Flujo de funcionamiento

1. El usuario abre la app React en el navegador.
2. `App.js` solicita el catálogo de cajas a `http://localhost:8080/api/productos`.
3. El BFF (`bff-service`) reenvía la solicitud a `producto-service` en `http://localhost:8081`.
4. El usuario agrega cajas al carrito.
5. Al confirmar, React envía un POST a `http://localhost:8080/api/compras`.
6. El BFF reenvía la compra a `compra-service` en `http://localhost:8082`.
7. `compra-service` calcula el total y guarda la compra en H2.

---

## 5. Configuración de puertos

- `bff-service`: no tiene `server.port` configurado directamente en el repositorio, pero el frontend espera `http://localhost:8080`.
- `producto-service`: `8081`
- `compra-service`: `8082`

---

## 6. Detalle de cada servicio

### 6.1 `bff-service`

#### Archivos clave
- `BackendParaFrontendApplication.java`
- `BackendParaFrontendControlador.java`
- `BackendParaFrontendService.java`
- `CompraDTO.java`
- `ProductoDTO.java`

#### Cómo funciona

El BFF expone:

- `GET /api/productos`
- `GET /api/productos/{id}`
- `POST /api/compras`

Y usa `WebClient` para comunicarse con los microservicios:

- `producto-service`: `/api/productos`
- `compra-service`: `/api/compras`

#### Código clave

```java
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BackendParaFrontendControlador {
    private final BackendParaFrontendService servicio;

    public BackendParaFrontendControlador(BackendParaFrontendService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarCajas() {
        return ResponseEntity.ok(servicio.obtenerProductos());
    }

    @PostMapping("/compras")
    public ResponseEntity<CompraDTO> registrarSuscripcion(@RequestBody CompraDTO compra) {
        CompraDTO resultado = servicio.procesarCompra(compra);
        return ResponseEntity.ok(resultado);
    }
}
```

```java
@Service
public class BackendParaFrontendService {
    private final WebClient clienteProductos;
    private final WebClient clienteCompras;

    public BackendParaFrontendService(
            WebClient.Builder constructorWebClient,
            @Value("${microservicio.producto.url:http://localhost:8081}") String urlProductos,
            @Value("${microservicio.compra.url:http://localhost:8082}") String urlCompras) {
        this.clienteProductos = constructorWebClient.baseUrl(urlProductos).build();
        this.clienteCompras = constructorWebClient.baseUrl(urlCompras).build();
    }

    public List<ProductoDTO> obtenerProductos() {
        ProductoDTO[] productos = clienteProductos.get()
                .uri("/api/productos")
                .retrieve()
                .bodyToMono(ProductoDTO[].class)
                .block();
        return productos != null ? Arrays.asList(productos) : List.of();
    }

    public CompraDTO procesarCompra(CompraDTO compra) {
        return clienteCompras.post()
                .uri("/api/compras")
                .bodyValue(compra)
                .retrieve()
                .bodyToMono(CompraDTO.class)
                .block();
    }
}
```

---

### 6.2 `producto-service`

#### Archivos clave
- `ProductoServiceApplication.java`
- `config/CargadorDatos.java`
- `controlador/ProductoControlador.java`
- `modelo/Producto.java`
- `repositorio/ProductoRepository.java`
- `servicio/ProductoServicio.java`
- `application.properties`

#### Funcionalidad
Gestiona el catálogo con operaciones CRUD y filtros básicos.

Endpoints expuestos:

- `GET /api/productos`
- `GET /api/productos/disponibles`
- `GET /api/productos/{id}`
- `POST /api/productos`
- `PUT /api/productos/{id}`
- `DELETE /api/productos/{id}`

#### Modelo `Producto`

```java
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
}
```

#### Carga inicial de datos

`CargadorDatos` inserta cinco productos iniciales en el catálogo si la base de datos está vacía.

#### Lógica de servicio

`ProductoServicio` implementa:

- `listarTodos()`
- `listarDisponibles()`
- `obtenerPorId(Long id)`
- `guardar(Producto producto)`
- `actualizar(Long id, Producto datos)`
- `eliminar(Long id)`
- `buscarPorNombre(String nombre)`
- `buscarPorCategoria(String categoria)`

---

### 6.3 `compra-service`

#### Archivos clave
- `CompraServiceApplication.java`
- `controlador/CompraControlador.java`
- `modelo/Compra.java`
- `modelo/ItemCompra.java`
- `repositorio/CompraRepository.java`
- `servicio/CompraServicio.java`
- `application.properties`

#### Funcionalidad
Gestiona compras/suscripciones y persiste la información en H2.

Endpoints expuestos:

- `POST /api/compras`
- `GET /api/compras`
- `GET /api/compras/{id}`

#### Modelo `Compra`

```java
@Entity
@Table(name = "compras")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "compra_id")
    private List<ItemCompra> items;

    private Double total;
    private String estado;
    private LocalDateTime fechaCreacion;
}
```

#### Proceso de registro de compra

`CompraServicio.registrarCompra(Compra compra)`:

- calcula el total con cada `ItemCompra.getSubtotal()`
- establece `estado = "COMPLETADA"`
- guarda la entidad en el repositorio

#### Modelo `ItemCompra`

```java
@Entity
@Table(name = "items_compra")
public class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;

    public Double getSubtotal() {
        return precioUnitario * cantidad;
    }
}
```

---

### 6.4 `frontend`

#### Archivos clave
- `package.json`
- `public/index.html`
- `src/index.js`
- `src/App.js`
- `src/App.css`
- `src/components/CatalogoCajas.jsx`
- `src/components/Carrito.jsx`

#### Comportamiento principal

- `App.js` mantiene el estado del carrito y la vista activa.
- `CatalogoCajas.jsx` obtiene productos desde el BFF y muestra el catálogo.
- `Carrito.jsx` muestra el carrito, el total y permite confirmar la suscripción.

#### Código clave

```javascript
const agregarAlCarrito = (producto) => {
  setProductosEnCarrito(prev => {
    const existente = prev.find(item => item.id === producto.id);
    if (existente) {
      return prev.map(item =>
        item.id === producto.id
          ? { ...item, cantidad: item.cantidad + 1 }
          : item
      );
    }
    return [...prev, { ...producto, cantidad: 1 }];
  });
};
```

```javascript
const finalizarCompra = async () => {
  const compra = {
    items: items.map(item => ({
      productoId: item.id,
      cantidad: item.cantidad,
      precioUnitario: item.precio,
    })),
    total: totalPrecio,
    estado: 'PENDIENTE',
  };

  await fetch('http://localhost:8080/api/compras', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(compra),
  });
};
```

#### UI y estilo

- Tema inspirado en verde orgánico.
- Layout responsivo sencillo con tarjetas de producto y resumen de carrito.
- Muestra estado de carga y manejo básico de error.

---

## 7. Archivos de configuración importantes

### `frontend/package.json`

```json
{
  "name": "greenbite-frontend",
  "version": "1.0.0",
  "private": true,
  "dependencies": {
    "react": "^18.2.0",
    "react-dom": "^18.2.0",
    "react-scripts": "5.0.1"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test"
  }
}
```

### `producto-service/src/main/resources/application.properties`

```properties
server.port=8081
spring.application.name=greenbite-producto-service
spring.datasource.url=jdbc:h2:mem:productosdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### `compra-service/src/main/resources/application.properties`

```properties
server.port=8082
spring.application.name=greenbite-compra-service
spring.datasource.url=jdbc:h2:mem:comprasdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

## 8. Cómo ejecutar el proyecto

1. Desde `producto-service/`: `mvn spring-boot:run`
2. Desde `compra-service/`: `mvn spring-boot:run`
3. Desde `bff-service/`: `mvn spring-boot:run`
4. Desde `frontend/`: `npm install && npm start`

Abre el navegador en `http://localhost:3000`.

---

## 9. Recomendaciones

- Verifica que los tres servicios Java estén corriendo antes de usar el frontend.
- El proyecto usa bases de datos en memoria H2; los datos se pierden al reiniciar los servicios.
- Si quieres convertir este documento a PDF, abre el archivo Markdown en VS Code y usa la extensión de exportación a PDF o un conversor Markdown.

---

## 10. Comentarios finales

Este repositorio implementa una arquitectura de microservicios simple con un BFF centralizado y un frontend React ligero. La comunicación entre servicios se realiza con REST, y la lógica de compra está diseñada para validar el total del pedido en el backend.
