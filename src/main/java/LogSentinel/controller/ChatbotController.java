package LogSentinel.controller;

import LogSentinel.dto.ChatbotRequest;
import LogSentinel.dto.ChatbotResponse;
import LogSentinel.service.ChatbotService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatbotResponse> chat(
            @Valid @RequestBody ChatbotRequest request) {

        ChatbotResponse response = chatbotService.chat(request);

        return ResponseEntity.ok(response);
    }
}
