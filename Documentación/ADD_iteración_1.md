# ADD Iteración 1: Estructura Arquitectónica General

## 1. Objetivo de la Iteración
El objetivo de esta primera iteración es definir el estilo arquitectónico base que soportará el sistema "Trotamundos". Al ser un sistema desde cero (Greenfield), debemos elegir la estructura que mejor se adapte a los requerimientos de mayor impacto.

## 2. Selección de Drivers Arquitectónicos
Para esta iteración, seleccionamos los requerimientos que tienen un impacto global en la estructura del sistema:
*   **QA1 - Escalabilidad:** 15 millones de cuentas, 2 millones activos, picos de tráfico dramáticos.
*   **QA3 - Disponibilidad:** Tolerancia máxima de 5 minutos de inactividad mensual.
*   **QA6 - Evolucionabilidad:** Facilidad para integrar nuevos medios de transporte en el futuro.
*   **UC8 (Avanzado) - Asistente Virtual (Chatbot):** Soporte interactivo en tiempo real, delegando a la agencia en casos complejos.

## 3. Concepto de Diseño Seleccionado
Para satisfacer la necesidad de escalar partes del sistema de forma independiente (ej. el escaneo de correos puede requerir más recursos que el chatbot o viceversa) y evitar puntos únicos de fallo que comprometan la disponibilidad, se ha decidido adoptar una **Arquitectura de Microservicios**. 

Se utilizará un **API Gateway** como punto de entrada unificado para clientes Web y Móviles, y comunicación asíncrona basada en eventos para tareas pesadas (como el procesamiento de correos y la analítica).

> **Decisión Arquitectónica:** Ver [ADR-001: Selección del Estilo Arquitectónico](adr-001-estilo-arquitectonico.md) para el detalle de esta decisión.

## 4. Elementos Instanciados y Asignación de Responsabilidades

En esta iteración inicial, identificamos los siguientes componentes lógicos (Módulos):

1.  **API Gateway / BFF (Backend for Frontend):** Enruta las peticiones web y móviles, maneja la autenticación y centraliza las conexiones (ej. WebSockets para el Chatbot).
2.  **Servicio de Gestión de Viajes (Core):** Maneja el ABM de los viajes y el agrupamiento del itinerario.
3.  **Servicio de Ingesta de Correos:** Sondea y escanea correos en segundo plano (Workers asíncronos).
4.  **Servicio de Integración de Terceros:** Actúa como capa de anticorrupción (ACL) para comunicarse con aerolíneas, hoteles y la agencia de viajes.
5.  **Servicio de Asistente Virtual (Chatbot):** Gestiona la lógica conversacional y determina cuándo derivar a soporte humano.
6.  **Servicio de Analítica:** Recopila datos de forma pasiva (escuchando eventos) para las consultas pesadas del equipo de datos, sin afectar el rendimiento del Core.

## 5. Próximos Pasos
En la Iteración 2, nos enfocaremos en el **Rendimiento (QA2)** y la **Interoperabilidad (QA4)**, diseñando cómo se comunicarán estos servicios internamente y cómo nos integraremos con los proveedores externos para cumplir con las actualizaciones en menos de 5 minutos.