# Proyecto_Trenes_ED - Sistema de Gestión de Trenes de Transporte de Pasajeros

Proyecto final del curso Estructuras de Datos 2026‑10
Universidad Pontificia Bolivariana Seccional Bucaramanga  
Autor: Sebastian Alberto Pinto Torres

## Descripción del proyecto

Este sistema cliente‑servidor automatiza la gestión de una flota ferroviaria de pasajeros. Permite administrar trenes, rutas y empleados, vender boletos con cálculo de la ruta más corta (Dijkstra), controlar el orden de abordaje y gestionar usuarios con autenticación segura. Todo está implementado usando exclusivamente estructuras de datos propias desarrolladas en el curso, sin bibliotecas de colecciones estándar.

## Funcionalidades principales

- CRUD de trenes, rutas y empleados con filtros, paginación y persistencia en archivos.
- Máquina de venta de boletos que muestra la distancia más corta entre dos estaciones y recomienda la ruta óptima.
- Orden de abordaje generado de atrás hacia adelante respetando categorías (Premium → Ejecutivo → Estándar).
- Autenticación con roles (ADMIN, OPERATOR, STANDARD_USER) y almacenamiento de contraseñas con hash SHA‑256.
- Interfaz gráfica con Swing (cliente) y panel de control del servidor.

## Tecnologías utilizadas

| Componente        | Tecnología                         |
|-------------------|------------------------------------|
| Lenguaje          | Java 25 LTS                        |
| Construcción      | Maven 3.9.11                       |
| Pruebas unitarias | JUnit 5                            |
| Comunicación      | Java RMI                           |
| Interfaz gráfica  | Swing (diseñada con NetBeans)      |
| Modelado          | UML (diagramas de clases, secuencia, componentes) |
| Persistencia      | Archivos .dat (serialización)    |

## Estructuras de datos propias empleadas

- **SinglyLinkedList** – para almacenar flota de trenes, vagones, listas en general.
- **Array (dinámico)** – gestión de asientos en cada vagón.
- **Stack** – para invertir el orden de los vagones en el abordaje.
- **PriorityQueue** – clasificación de pasajeros por categoría.
- **HashTable** – almacenamiento de usuarios y tickets.
- **GraphMatrix + Dijkstra** – cálculo de la ruta más corta entre estaciones.

## Arquitectura

Ambos lados (cliente y servidor) siguen el patrón **Modelo‑Vista‑Controlador (MVC)**.

- **Servidor:** expone servicios remotos mediante TicketInterface.  
  - SalesManager – lógica de negocio (transacciones, rutas).  
  - SecurityModule – autenticación y sesiones.  
  - PersistenceModule – guardado/carga de datos en archivos.
- **Cliente:** interfaz Swing con vistas para login, compra de boletos y administración.

## Requisitos para ejecución

- JDK 25 o superior.
- Maven 3.9.11+.
- Puerto 1808 disponible (configurable en config.properties).

## Instalación y ejecución

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/SebastianXSZ/Proyecto_Trenes_ED
   cd train-management
   ```

2. **Compila y empaqueta:**
   ```bash
   mvn clean package -DskipTests
   ```

3. **Inicia el servidor:**
   ```bash
   java -cp "target/classes;target/dependency/*" edu.upb.AppServer
   ```
   Presiona el botón **Start Server** en la ventana de control.

4. **Inicia el cliente (en otra terminal):**
   ```bash
   java -cp "target/classes;target/dependency/*" edu.upb.AppClient
   ```
   Usa las credenciales de prueba para ingresar.

## Credenciales de prueba

| Usuario   | Contraseña | Rol          |
|-----------|------------|--------------|
| admin     | 1234       | ADMIN        |
| operador  | 123        | OPERATOR     |
| user      | 123        | STANDARD_USER (registrarse desde el login) |

## Pruebas unitarias

```bash
mvn test
```

Se incluyen pruebas para:
- PassengerWagon – asignación de asientos.
- SalesManager – procesamiento de transacciones con rutas.
- SecurityModule – validación de credenciales.

## Evidencia del sistema

[![Ver video del funcionamiento](https://img.youtube.com/vi/XXXXXXX/0.jpg)](https://youtu.be/XXXXXXX)

## Documentación adicional

- Especificación de requerimientos y diagramas UML.
- Artículo científico en formato LNCS/CCIS.
- Bitácora de seguimiento en GitHub.

## Licencia

Este proyecto es de uso académico. Todos los derechos reservados.