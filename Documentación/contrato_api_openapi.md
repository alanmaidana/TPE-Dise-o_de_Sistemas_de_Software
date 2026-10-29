openapi: 3.0.0
info:
  title: API Trotamundos - Core de Viajes
  description: Contrato Spec-driven para el microservicio de Gestión de Viajes (Core).
  version: 1.0.0
servers:
  - url: http://localhost:8080/api/v1
    description: Entorno de Prototipo Local
paths:
  /viajes:
    get:
      summary: Obtiene los viajes agrupados del usuario
      operationId: getViajes
      responses:
        '200':
          description: Lista de viajes obtenida exitosamente
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Viaje'
    post:
      summary: Agregar una reserva manualmente (UC4)
      operationId: addViaje
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ViajeInput'
      responses:
        '201':
          description: Viaje creado exitosamente
components:
  schemas:
    Viaje:
      type: object
      properties:
        id:
          type: string
          format: uuid
        titulo:
          type: string
          example: "Vacaciones en Bariloche"
        estado:
          type: string
          enum: [ACTIVO, ARCHIVADO]
        fecha_inicio:
          type: string
          format: date
    ViajeInput:
      type: object
      required:
        - titulo
        - fecha_inicio
      properties:
        titulo:
          type: string
          example: "Viaje de Negocios a CABA"
        fecha_inicio:
          type: string
          format: date