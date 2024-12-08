package com.example.chat.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.chat.model.ChatMessage;

@Controller
@CrossOrigin(origins = "http://localhost:4202")
public class chatController {
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println(message);
        // changes made
        return new ChatMessage(message.getMessage(), message.getUser());
    }
    @MessageMapping("/send")
    @SendTo("/topic/signaling")
    public String handleSignalMessage(String message) {
        return message; // Broadcast the signaling message to all connected clients
    }
}
