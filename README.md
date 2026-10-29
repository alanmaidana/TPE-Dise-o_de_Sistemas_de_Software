# 🌍 Trotamundos - Sistema de Gestión de Viajes

Prototipo de Software desarrollado para el **Trabajo Práctico Especial (TPE)**. Este sistema implementa una arquitectura orientada a servicios, aplicando un enfoque de diseño guiado por contratos y patrones de diseño orientados a objetos.

## 🏛️ Decisiones de Arquitectura y Patrones
* **Estilo Arquitectónico:** Microservicios / Modular Monolith enfocado en la separación de responsabilidades.
* **Patrón de Diseño (GoF):** Se implementó el **Patrón Strategy** (`ProveedorStrategy`) en la capa de integración para aislar de forma limpia y extensible la lógica de comunicación con múltiples proveedores externos de viajes (aerolíneas, hoteles, etc.).
* **Contratos:** Desarrollo basado en especificaciones (*Spec-driven*) utilizando OpenAPI/Swagger.

## 🛠️ Tecnologías Utilizadas
* **Java 17**
* **Spring Boot 3.1.5** (Web REST API)
* **Maven** (Gestión de dependencias y construcción)

## 📂 Estructura del Proyecto
```text
prototipo/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── trotamundos/
│                   ├── api/             # Controladores REST
│                   ├── estrategia/      # Implementación del Patrón Strategy
│                   └── TrotamundosApplication.java # Clase de arranque
├── pom.xml                              # Configuración de Maven y dependencias
└── README.md
