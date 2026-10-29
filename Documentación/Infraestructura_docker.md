version: '3.8'

services:
  # Base de Datos para el Servicio Core de Viajes
  postgres-core:
    image: postgres:15-alpine
    container_name: trotamundos-db
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: password123
      POSTGRES_DB: viajes_core
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
    networks:
      - trotamundos-net

  # Message Broker para simular la Arquitectura Orientada a Eventos
  rabbitmq:
    image: rabbitmq:3-management-alpine
    container_name: trotamundos-broker
    environment:
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: password123
    ports:
      - "5672:5672"   # Puerto para comunicación de microservicios
      - "15672:15672" # Puerto para el panel de administración UI
    networks:
      - trotamundos-net

volumes:
  pgdata:

networks:
  trotamundos-net:
    driver: bridge