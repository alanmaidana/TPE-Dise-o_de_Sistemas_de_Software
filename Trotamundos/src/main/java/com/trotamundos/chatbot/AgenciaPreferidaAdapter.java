package com.trotamundos.chatbot;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class AgenciaPreferidaAdapter {

    public String escalarCaso(ConsultaChatbot consulta) {
        String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "Escalado a Agencia de Viajes Preferida. Ticket generado: " + ticketId +
               " | Viaje: " + consulta.getViajeId() +
               " | Motivo: " + consulta.getDetalle();
    }
}