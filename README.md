# 🛒 MercadoSpring

API REST desarrollada con **Java + Spring Boot** para la gestión de un supermercado.

El proyecto fue realizado con fines educativos con el objetivo de practicar el desarrollo de aplicaciones backend utilizando una arquitectura en capas, persistencia con JPA/Hibernate, validaciones, manejo de excepciones personalizadas y exposición de recursos mediante una API REST.

---

# 🚀 Tecnologías utilizadas

- Java 25
- Spring Boot
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Lombok
- Maven
- MariaDB / MySQL
- Git
- GitHub
- IntelliJ IDEA
- Postman

---

# 📚 Características

El sistema permite administrar:

- Productos
- Sucursales
- Ventas
- Detalles de Venta

Además incorpora:

- Validaciones mediante Jakarta Validation.
- Excepciones personalizadas.
- DTOs para desacoplar la capa de persistencia.
- Mappers manuales entre entidades y DTOs.
- Arquitectura por capas.
- Relaciones JPA entre entidades.
- Control de stock.
- Optimistic Locking mediante `@Version`.

---

# 🏛 Arquitectura

El proyecto está organizado siguiendo una arquitectura en capas.

```
Controller
      │
      ▼
 Service
      │
      ▼
Repository
      │
      ▼
 Base de Datos
```

### Organización del proyecto

```
src
└── main
    ├── controller
    ├── dto
    ├── exception
    ├── mapper
    ├── model
    │      └── enums
    ├── repository
    ├── service
    │      └── interfaces
    └── config
```

---

# 🗄 Modelo de datos

El proyecto posee cuatro entidades principales.

## Producto

Representa un producto del supermercado.

Campos principales

- idProducto
- nombreProducto
- enumCategoriaProducto
- precioProducto
- cantidadProducto

---

## Sucursal

Representa una sucursal del supermercado.

Campos principales

- idSucursal
- nombreSucursal
- direccionSucursal

---

## Venta

Representa una venta realizada.

Campos principales

- idVenta
- fechaVenta
- estadoVenta
- total
- sucursal

Una venta puede contener múltiples detalles.

---

## DetalleVenta

Representa cada producto vendido dentro de una venta.

Campos principales

- idDetalleVenta
- producto
- cantidad
- precioUnitario
- subtotal

---

# ⚙ Configuración

Modificar el archivo:

```
application.properties
```

con los datos de la base de datos.

Ejemplo

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/supermercado_spring
spring.datasource.username=root
spring.datasource.password=******
```

---

# ▶ Ejecutar el proyecto

Clonar el repositorio

```bash
git clone https://github.com/AlanCasasDev/MercadoSpring.git
```

Entrar al proyecto

```bash
cd MercadoSpring
```

Ejecutar

```bash
mvn spring-boot:run
```

La API quedará disponible en

```
http://localhost:8080
```

---

# 📦 Endpoints

---

# Productos

## Obtener todos

```
GET /productos
```

---

## Obtener por id

```
GET /productos/{id}
```

---

## Crear producto

```
POST /productos/crear_producto
```

### Enum Categoria Producto Validos
```
ALMACEN / BEBIDAS / GASEOSAS / AGUAS / JUGOS / VINOS / CERVEZAS / LACTEOS / QUESOS / FIAMBRES / 
CARNES / POLLO / PESCADOS / FRUTAS / VERDURAS / PANADERIA / PASTAS / CONGELADOS / SNACKS / 
GOLOSINAS / DESAYUNO_Y_MERIENDA / LIMPIEZA / HIGIENE_PERSONAL / PERFUMERIA / BEBES / MASCOTAS /
BAZAR /  LIBRERIA / ELECTRODOMESTICOS
```

### Body

```json
{
  "nombreProducto": "Arroz 1kg",
  "enumCategoriaProducto": "ALMACEN",
  "precioProducto": 1800.00,
  "cantidadProducto": 300
}
```

---

## Actualizar producto

```
PUT /productos/{id}
```

### Body

```json
{
  "idProducto": 1,
  "nombreProducto": "Arroz Premium",
  "enumCategoriaProducto": "ALMACEN",
  "precioProducto": 2000.00,
  "cantidadProducto": 350
}
```

---

## Eliminar producto

```
DELETE /productos/{id}
```

---

## Consultar stock

```
GET /productos/{id}/cantidad
```

---

## Aumentar stock

```
PATCH /productos/{id}/stock/aumentar?cantidad=50
```

Ejemplo

```
PATCH /productos/1/stock/aumentar?cantidad=25
```

---

## Reducir stock

```
PATCH /productos/{id}/stock/reducir?cantidad=10
```

Ejemplo

```
PATCH /productos/1/stock/reducir?cantidad=5
```

---

## Vaciar stock

```
PATCH /productos/{id}/stock/vaciar
```

---

# Sucursales

## Obtener todas

```
GET /sucursales
```

---

## Obtener por id

```
GET /sucursales/{id}
```

---

## Crear sucursal

```
POST /sucursales/crear_sucursal
```

### Body

```json
{
  "nombreSucursal": "Sucursal Centro",
  "direccionSucursal": "Av. Corrientes 1000"
}
```

---

## Actualizar sucursal

```
PUT /sucursales/{id}
```

### Body

```json
{
  "idSucursal": 1,
  "nombreSucursal": "Sucursal Centro",
  "direccionSucursal": "Av. Corrientes 1200"
}
```

---

## Eliminar sucursal

```
DELETE /sucursales/{id}
```

---

# Ventas

## Obtener todas

```
GET /ventas
```

---

## Obtener por id

```
GET /ventas/{id}
```

---

## Crear venta

```
POST /ventas/crear_venta
```

### Enum Estado Venta Validos
```
PENDIENTE
CONFIRMADA
PAGADA
PREPARANDO
LISTA_PARA_ENTREGA
ENTREGADA
COMPLETADA
CANCELADA
RECHAZADA
REEMBOLSADA
```

### Body

```json
{
  "estadoVenta": "PENDIENTE",
  "idSucursal": 2,
  "detalles": [
    {
      "idProducto": 1,
      "cantidad": 2
    },
    {
      "idProducto": 5,
      "cantidad": 10
    }
  ]
}
```

---

## Actualizar venta

```
PUT /ventas/{id}
```

Utiliza el mismo formato JSON que el POST.

---

## Eliminar venta

```
DELETE /ventas/{id}
```

---

# ✅ Validaciones

El proyecto utiliza **Jakarta Validation**.

Algunas de las validaciones implementadas son:

- `@NotNull`
- `@NotBlank`
- `@Positive`
- `@PositiveOrZero`
- `@Size`

---

# ⚠ Manejo de excepciones

La API utiliza un manejador global de excepciones mediante `@RestControllerAdvice`.

Entre las excepciones implementadas se encuentran:

- ProductoNoEncontradoException
- ProductoDuplicadoException
- StockProductoNullException
- StockProductoInsuficienteException
- SucursalNoEncontradaException
- NombreSucursalDuplicadaException
- DireccionSucursalDuplicadaException

Las respuestas devuelven los códigos HTTP apropiados junto con un mensaje descriptivo.

---

# 🔮 Posibles Mejoras futuras

Entre las posibles mejoras previstas para el proyecto se encuentran:

- Migración completa a convenciones REST.
- Incorporación de autenticación mediante Spring Security.
- Documentación con OpenAPI / Swagger.
- Pruebas unitarias e integración.
- Contenerización mediante Docker.

---

# 👨‍💻 Autor

**Alan Casas**

Proyecto desarrollado con fines educativos para la práctica de Spring Boot, JPA e implementación de APIs REST.


## Estado del proyecto

🚧 En desarrollo

Este proyecto continúa evolucionando como parte de mi aprendizaje en Java Backend y Spring Boot. En futuras versiones se incorporarán mejoras relacionadas con buenas prácticas REST, seguridad, testing automatizado y optimización de la lógica de negocio.