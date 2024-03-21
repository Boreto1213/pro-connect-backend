package fontys.sem3.proconnectbackend.controller;

import fontys.sem3.proconnectbackend.business.dtos.GetChatOptionsResponse;
import fontys.sem3.proconnectbackend.business.exeptions.InvalidUserIdException;
import fontys.sem3.proconnectbackend.configuration.webscoket.WebSocketService;
import fontys.sem3.proconnectbackend.domain.Message;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:5173")
public class MessageController {
    private final WebSocketService webSocketService;

    @GetMapping("/chats")
    @Transactional
    public ResponseEntity<GetChatOptionsResponse> getChats(@RequestParam(name = "userId") @NotNull Long userId) {
        try {
            GetChatOptionsResponse response = webSocketService.getChatOptions(userId);
            return ResponseEntity.ok().body(response);
        } catch (InvalidUserIdException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/chat-history")
    public ResponseEntity<List<Message>> getChatHistory(@RequestParam(name = "user1") @NotNull Long user1,
                                        @RequestParam(name = "user2") @NotNull Long user2) {
        List<Message> response = webSocketService.getChatHistory(user1, user2);
        return ResponseEntity.ok().body(response);
    }

    @MessageMapping("/direct-message")
    @Transactional
    public void handleDirectMessage(@Payload Message message) {
        // how to handle errors here?
        webSocketService.sendDirectMessage(message);
    }
}