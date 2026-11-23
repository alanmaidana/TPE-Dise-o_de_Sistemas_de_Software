package com.trotamundos.chatbot;

import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    private final ManejadorSoporte cadenaSoporte;

    public ChatbotService(SoporteAutomatizadoHandler nivel1, EscalamientoAgenciaHandler nivel2) {
        // Armamos la cadena: Nivel 1 -> Nivel 2
        nivel1.setSiguiente(nivel2);
        this.cadenaSoporte = nivel1;
    }

    public String procesarConsulta(ConsultaChatbot consulta) {
        return cadenaSoporte.manejar(consulta);
    }
}