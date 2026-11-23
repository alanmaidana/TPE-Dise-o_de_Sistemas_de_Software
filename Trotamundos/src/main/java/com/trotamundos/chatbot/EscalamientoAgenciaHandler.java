package com.trotamundos.chatbot;

import org.springframework.stereotype.Component;

@Component
public class EscalamientoAgenciaHandler extends ManejadorSoporte {

    private final AgenciaPreferidaAdapter agenciaAdapter;

    public EscalamientoAgenciaHandler(AgenciaPreferidaAdapter agenciaAdapter) {
        this.agenciaAdapter = agenciaAdapter;
    }

    @Override
    public String manejar(ConsultaChatbot consulta) {
        // Cualquier caso que llegue hasta aquí se considera complejo y se deriva
        String resultadoEscalamiento = agenciaAdapter.escalarCaso(consulta);
        return "[Chatbot Nivel 2 - Derivación Humana] " + resultadoEscalamiento;
    }
}