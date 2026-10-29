# ADD Iteración 2: Patrones de Comunicación y Gestión de Datos

## 1. Objetivo de la Iteración
El objetivo de esta iteración es definir cómo se comunicarán los microservicios internamente y cómo se gestionarán los datos para aislar las cargas de trabajo operacionales (usuarios consultando viajes) de las cargas analíticas (equipo de datos).

## 2. Selección de Drivers Arquitectónicos
*   **QA2 - Rendimiento:** Tiempo de respuesta web < 800ms. Las novedades de proveedores deben verse en la app en < 5 minutos.
*   **QA4 - Interoperabilidad:** Integración fluida y constante con proveedores externos.
*   **UC7 - Analítica de Datos:** Consultas analíticas pesadas para monetización de datos.

## 3. Conceptos de Diseño Seleccionados

1.  **Arquitectura Orientada a Eventos (Event-Driven) para comunicación interna:** Los servicios no se llamarán entre sí sincrónicamente (REST) bloqueando hilos, sino que emitirán eventos a un Message Broker (ej. Apache Kafka o RabbitMQ). Esto absorbe los picos de tráfico.
    *   > *Ver [ADR-002: Patrón de Comunicación Asíncrona]*
2.  **Patrón CQRS (Command Query Responsibility Segregation):** Separaremos las bases de datos de escritura (operacionales) de las bases de datos de lectura (analíticas). El motor analítico consumirá eventos del broker para poblar su propia base de datos optimizada para lecturas (ej. Data Warehouse/OLAP).
    *   > *Ver [ADR-003: Aplicación de Patrón CQRS para Analítica]*
3.  **Polling Asíncrono / Webhooks para Integraciones:** Para lograr los 5 minutos de actualización (QA2), el *Servicio de Integración* suscribirá a Webhooks de las agencias/aerolíneas si es posible, o implementará un mecanismo de sondeo recurrente y optimizado.

## 4. Elementos Instanciados y Refinamiento (Diagrama de Contenedores)

```mermaid
C4Container
  title Diagrama de Contenedores (Iteración 2) - Comunicación y Datos

  Person(user, "Viajero", "App Web/Mobile")
  Person(data_team, "Equipo de Datos", "Ejecuta analítica pesada")
  
  System_Boundary(trotamundos, "Trotamundos (Backend)") {
    Container(api_gw, "API Gateway", "Kong / NGINX", "Maneja peticiones síncronas REST/GraphQL y WebSockets (Chatbot).")
    
    Container(viajes_svc, "Gestión de Viajes (Core)", "Microservicio", "Procesa lógica de negocio y ABM de viajes.")
    ContainerDb(db_core, "DB Operacional", "PostgreSQL / MongoDB", "Almacena el estado actual de los viajes.")
    
    Container(integracion_svc, "Integración de Terceros", "Microservicio", "Adaptador (ACL) para APIs externas.")
    Container(mail_svc, "Ingesta de Correos", "Microservicio (Worker)", "Sondea y parsea correos de forma asíncrona.")
    
    Container(chatbot_svc, "Asistente Virtual", "Microservicio", "Motor NLP y lógica del Chatbot.")
    
    ContainerQueue(event_broker, "Event Broker", "Apache Kafka", "Bus de eventos central. Maneja la comunicación asíncrona.")
    
    Container(analitica_svc, "Servicio de Analítica", "Microservicio", "Consume eventos y prepara datos analíticos.")
    ContainerDb(db_analitica, "DB Analítica (OLAP)", "ClickHouse / BigQuery", "Optimizada para consultas pesadas.")
  }

  System_Ext(email_provider, "Proveedores Correo", "IMAP/API")
  System_Ext(travel_providers, "Proveedores Viajes", "APIs (Aerolíneas, Hoteles)")

  Rel(user, api_gw, "REST / WebSockets")
  Rel(api_gw, viajes_svc, "Enruta peticiones", "REST")
  Rel(api_gw, chatbot_svc, "Enruta chat", "WS/REST")
  
  Rel(viajes_svc, db_core, "Lee/Escribe")
  Rel(integracion_svc, travel_providers, "Consulta/Webhooks")
  Rel(mail_svc, email_provider, "Sondea correos")
  
  Rel(integracion_svc, event_broker, "Publica 'ReservaActualizada'", "Async")
  Rel(mail_svc, event_broker, "Publica 'ReservaExtraida'", "Async")
  Rel(viajes_svc, event_broker, "Consume/Publica eventos", "Async")
  
  Rel(analitica_svc, event_broker, "Consume todos los eventos", "Async")
  Rel(analitica_svc, db_analitica, "Pobla modelo de datos", "ETL/Stream")
  Rel(data_team, db_analitica, "Consultas analíticas SQL", "Directo/Herramienta BI")
```

## 5. Próximos Pasos
En la Iteración 3, profundizaremos en el **Procesamiento de Correos (QA5 - Seguridad)** para manejar credenciales de forma segura, y en cómo el prototipo será testeado garantizando el contrato de las APIs usando Spec-Driven Development.