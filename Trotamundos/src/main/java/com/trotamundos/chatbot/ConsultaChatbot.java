package com.trotamundos.chatbot;

public class ConsultaChatbot {
    private String viajeId;
    private String proveedor;
    private String intencion; // Ej: "ESTADO", "REPROGRAMAR", "CANCELACION_CRITICA"
    private String detalle;

    public ConsultaChatbot(String viajeId, String proveedor, String intencion, String detalle) {
        this.viajeId = viajeId;
        this.proveedor = proveedor;
        this.intencion = intencion;
        this.detalle = detalle;
    }

    public String getViajeId() { return viajeId; }
    public String getProveedor() { return proveedor; }
    public String getIntencion() { return intencion; }
    public String getDetalle() { return detalle; }
}