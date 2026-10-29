package com.trotamundos.api;

import com.trotamundos.estrategia.ProveedorStrategy;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/viajes")
public class ViajesController {

    private final List<ProveedorStrategy> estrategias;

    public ViajesController(List<ProveedorStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    @GetMapping("/{id}")
    public String obtenerViaje(@PathVariable String id, @RequestParam String proveedor) {
        // Aplica el Patrón Strategy limpio
        ProveedorStrategy estrategia = estrategias.stream()
                .filter(e -> e.soporta(proveedor))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Proveedor inválido"));
        
        String resultado = estrategia.consultarReserva(id);

        return "Procesando correctamente con Strategy: " + resultado;
    }
}