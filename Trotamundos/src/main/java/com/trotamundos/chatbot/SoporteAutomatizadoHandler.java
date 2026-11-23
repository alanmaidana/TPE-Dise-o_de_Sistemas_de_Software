package com.trotamundos.chatbot;

import com.trotamundos.estrategia.ProveedorStrategy;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SoporteAutomatizadoHandler extends ManejadorSoporte {

    private final List<ProveedorStrategy> estrategias;

    public SoporteAutomatizadoHandler(List<ProveedorStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    @Override
    public String manejar(ConsultaChatbot consulta) {
        String intencion = consulta.getIntencion().toUpperCase();

        // Nivel 1 resuelve consultas de estado y reprogramaciones menores
        if (intencion.equals("ESTADO") || intencion.equals("REPROGRAMAR")) {
            ProveedorStrategy estrategia = estrategias.stream()
                    .filter(e -> e.soporta(consulta.getProveedor()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Proveedor no soportado: " + consulta.getProveedor()));

            String respuestaProveedor = estrategia.consultarReserva(consulta.getViajeId());
            return "[Chatbot Nivel 1 - Resolución Automática] Intención '" + intencion +
                   "' procesada con Strategy -> " + respuestaProveedor;
        }

        // Si es un problema complejo, pasa al siguiente eslabón de la cadena
        if (siguiente != null) {
            return siguiente.manejar(consulta);
        }

        return "No se pudo procesar la solicitud.";
    }
}