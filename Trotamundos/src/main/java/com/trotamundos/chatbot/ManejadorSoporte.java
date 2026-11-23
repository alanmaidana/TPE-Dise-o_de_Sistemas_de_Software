package com.trotamundos.chatbot;

public abstract class ManejadorSoporte {
    protected ManejadorSoporte siguiente;

    public void setSiguiente(ManejadorSoporte siguiente) {
        this.siguiente = siguiente;
    }

    public abstract String manejar(ConsultaChatbot consulta);
}