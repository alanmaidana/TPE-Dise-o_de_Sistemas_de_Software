## Iteración 5: Incorporación del Módulo Avanzado - Asistente Virtual (Chatbot)

### 1. Objetivo de la Iteración
Abordar el requerimiento funcional avanzado obligatorio (**Opción B: Asistente Virtual**) asegurando el cumplimiento de los atributos de calidad de **Latencia (< 800ms)**, **Interoperabilidad** y **Evolucionabilidad**.

### 2. Selección de Elementos y Tácticas Arquitectónicas
* **Elemento a refinar:** `Trotamundos Backend` (Capa de Servicios de Negocio e Integración).
* **Tácticas aplicadas:**
  * *Encapsular / Usar intermediario (Interoperabilidad y Evolucionabilidad):* Uso de `AgenciaPreferidaAdapter` para abstraer los protocolos de las distintas agencias de viajes al momento de escalar tickets complejos.
  * *Diferir en el tiempo / Procesamiento acotado (Rendimiento):* Resolución síncrona rápida para consultas de primer nivel mediante estrategias en memoria y derivación asíncrona de contexto completo cuando se escala a soporte humano.
* **Patrones de Diseño:** *Chain of Responsibility* (evaluación de complejidad del reclamo) + *Strategy* (ejecución sobre proveedores).

### 3. Resumen de Cambios en Vistas
* Se incorpora el componente `ChatbotService` expuesto vía `/api/chatbot`.
* Se conecta `ChatbotService` con el componente existente `GestorEstrategiasProveedor` (para nivel 1) y con el nuevo componente `AgenciaPreferidaAdapter` (para nivel 2).

```mermaid
flowchart TB
    subgraph Clientes
        Web["Aplicación Web"]
        Mobile["App Móvil"]
    end

    subgraph Backend["Sistema Trotamundos (Backend)"]
        Gateway["API Gateway / REST Controllers"]
        ViajesSrv["ViajesService"]
        ChatbotSrv["ChatbotService (Chain of Responsibility)"]
        StrategyMgr["ProveedorStrategy (Aerolínea / Hotel / Auto)"]
        AgenciaAdapter["AgenciaPreferidaAdapter"]
    end

    subgraph Externos["Sistemas Externos"]
        ProvAPI["APIs de Proveedores (Aerolíneas/Hoteles)"]
        AgenciaAPI["API Agencia de Viajes Preferida"]
    end

    Web -->|HTTPS / JSON| Gateway
    Mobile -->|HTTPS / JSON| Gateway

    Gateway --> ViajesSrv
    Gateway --> ChatbotSrv

    ViajesSrv --> StrategyMgr
    ChatbotSrv -->|"1. Nivel 1: Consulta / Reprogramación"| StrategyMgr
    ChatbotSrv -->|"2. Nivel 2: Escalamiento Complejo"| AgenciaAdapter

    StrategyMgr -->|REST / OpenAPI| ProvAPI
    AgenciaAdapter -->|REST / Webhook| AgenciaAPI
```

```mermaid
sequenceDiagram
    autonumber
    actor Viajero
    participant API as ChatbotController
    participant Bot as ChatbotService
    participant Strat as ProveedorStrategy
    participant Agencia as AgenciaPreferidaAdapter

    Viajero->>API: POST /api/chatbot/mensaje (viajeId, intencion, proveedor)
    API->>Bot: procesarConsulta(mensaje)

    alt Caso Menor (Estado o Reprogramación simple)
        Bot->>Strat: soporta(proveedor) & ejecutarAccion(viajeId)
        Strat-->>Bot: Respuesta confirmada del proveedor
        Bot-->>API: Respuesta automática Nivel 1 (< 800ms)
        API-->>Viajero: "Consulta resuelta: [Detalle]"
    else Caso Complejo (Incidencia crítica / Fuera de alcance)
        Bot->>Agencia: escalarCaso(viajeId, historialChat)
        Agencia-->>Bot: Ticket generado (Nro. Derivación)
        Bot-->>API: Confirmación de escalamiento Nivel 2
        API-->>Viajero: "Caso derivado a su agencia preferida. Ticket #123"
    end
```