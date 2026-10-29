package com.trotamundos.estrategia;
import org.springframework.stereotype.Component;

@Component
public class AerolineaStrategy implements ProveedorStrategy {
    @Override
    public boolean soporta(String proveedor) {
        return "AEROLINEA".equalsIgnoreCase(proveedor);
    }
    @Override
    public String consultarReserva(String idReserva) {
        return "Vuelo 123 Confirmado por API Externa";
    }
}