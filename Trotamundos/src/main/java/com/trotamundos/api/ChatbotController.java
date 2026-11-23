package com.trotamundos.api;

import com.trotamundos.chatbot.ChatbotService;
import com.trotamundos.chatbot.ConsultaChatbot;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @GetMapping("/consulta")
    public String consultarBot(
            @RequestParam String viajeId,
            @RequestParam String proveedor,
            @RequestParam String intencion,
            @RequestParam(defaultValue = "Consulta estándar desde panel") String detalle) {

        ConsultaChatbot consulta = new ConsultaChatbot(viajeId, proveedor, intencion, detalle);
        return chatbotService.procesarConsulta(consulta);
    }
}