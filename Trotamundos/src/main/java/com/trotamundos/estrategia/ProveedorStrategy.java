package com.trotamundos.estrategia;

public interface ProveedorStrategy {
    boolean soporta(String proveedor);
    String consultarReserva(String idReserva);
}