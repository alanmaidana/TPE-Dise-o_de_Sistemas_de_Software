# Alcance del Prototipo (Proof of Concept)

## 1. Objetivo del Prototipo
El objetivo de esta implementación no es construir el sistema completo listo para producción, sino desarrollar un prototipo funcional que demuestre la viabilidad de la arquitectura diseñada (Microservicios, Comunicación por Eventos y Spec-Driven) dentro de los tiempos estipulados por el TPE.

## 2. Acotamiento del Alcance (Scope)
Para asegurar la viabilidad de las entregas, el equipo construirá únicamente el "Camino Feliz" (Happy Path) de los componentes centrales. 

**Componentes que SÍ se implementarán:**
1. **API Gateway (Simulado):** Un punto de entrada simple (ej. usando Express.js, FastAPI o Nginx) que enrute las peticiones.
2. **Servicio de Gestión de Viajes (Core):** Implementará el ABM manual de viajes (UC4). Este servicio **debe** ser programado utilizando el enfoque Spec-driven (el código debe basarse en un contrato OpenAPI).
3. **Servicio de Ingesta/Integración (Mock):** Un script o microservicio simplificado que simule recibir actualizaciones de aerolíneas y publique un evento de prueba en el Message Broker.
4. **Message Broker Ligero:** En lugar de desplegar un clúster pesado de Apache Kafka, para el prototipo se utilizará **RabbitMQ** o **Redis Pub/Sub** corriendo en Docker para demostrar la comunicación asíncrona y el desacoplamiento (validando el ADR-002).

**Componentes que NO se implementarán (Fuera de alcance para código):**
* Autenticación OAuth 2.0 real con Google/Microsoft (se mockeará un usuario estático).
* Despliegue en Kubernetes (se usará `docker-compose` local para levantar el entorno).
* Servicio de Analítica y base de datos OLAP (ClickHouse/BigQuery).
* NLP real para el Chatbot (si se incluye el bot en el código, responderá a comandos estáticos y derivará el caso mockeando la respuesta).

## 3. Demostración del Spec-Driven Development
1. El equipo redactará el contrato `openapi-viajes.yaml`.
2. Se generarán los modelos de datos y esqueletos de controladores a partir de este archivo.
3. El frontend (o herramientas como Postman) consumirá la API respetando estrictamente este contrato.