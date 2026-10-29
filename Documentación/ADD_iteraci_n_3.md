# ADD Iteración 3: Seguridad, Privacidad y Spec-Driven Development

## 1. Objetivo de la Iteración
El sistema procesa información personal altamente sensible y accede a las bandejas de entrada de los usuarios. Esta iteración define los mecanismos de seguridad para garantizar la privacidad y autorizaciones, así como el flujo de desarrollo "Spec-driven" exigido por los requerimientos para asegurar la consistencia.

## 2. Selección de Drivers Arquitectónicos
*   **QA5 - Seguridad y Privacidad:** Acceso seguro a correos; compartición de itinerarios solo con personas autorizadas.
*   **QA6 - Evolucionabilidad y Mantenibilidad:** Uso de contratos (Spec-driven) para alta trazabilidad.
*   **UC1 - Procesamiento de Correo:** Escaneo de correos relacionados con viajes.

## 3. Conceptos de Diseño Seleccionados

1.  **Autorización Delegada (OAuth 2.0 / OIDC):** Nunca almacenaremos las contraseñas de los proveedores de correo de los usuarios. Utilizaremos OAuth 2.0 para obtener tokens de acceso (Access Tokens y Refresh Tokens) con el *scope* mínimo necesario (solo lectura de correos).
    *   > *Ver [ADR-004: Autorización para Acceso a Correos]*
2.  **API-First (Spec-driven Development):** Antes de programar, los equipos definirán los contratos de las APIs en formatos estándar. Esto es mandatorio según la especificación del TPE. 
    *   Utilizaremos **OpenAPI 3.0** para las APIs REST sincrónicas (comunicación Gateway -> Microservicios).
    *   Utilizaremos **AsyncAPI** para los contratos de los eventos en Kafka.
    *   > *Ver [ADR-005: Enfoque Spec-Driven Development]*
3.  **Gestión de Secretos:** Las credenciales de APIs de terceros (aerolíneas, agencias) y las claves de cifrado de la base de datos se almacenarán en un gestor de secretos externo (ej. HashiCorp Vault o AWS Secrets Manager), inyectándose en los microservicios solo en tiempo de ejecución.
4.  **Control de Acceso Basado en Roles/Atributos (RBAC/ABAC):** Para compartir un itinerario, se generarán tokens JWT específicos o enlaces firmados criptográficamente con una expiración temporal y vinculados a los identificadores de los usuarios destino, garantizando la privacidad (QA5).

## 4. Próximos Pasos
En la Iteración 4 (final), abordaremos la estrategia de infraestructura y despliegue para garantizar el *uptime* requerido y los picos masivos de uso (QA1, QA3), y revisaremos la integración arquitectónica del Chatbot.