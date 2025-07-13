package com.example.chat.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.chat.dto.CreateRoomRequest;
import com.example.chat.dto.JoinRoomRequest;
import com.example.chat.model.ChatMessage;
import com.example.chat.model.Room;
import com.example.chat.model.SignalMessage;
import com.example.chat.service.RoomService;

@Controller
@CrossOrigin(origins = "http://localhost:4202")
public class chatController {
	
	
	@Autowired
	private RoomService roomService;
	
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
    
    
    @MessageMapping("/signal/{roomId}")
    @SendTo("/topic/signal/{roomId}")
    public SignalMessage relaySignal(@DestinationVariable String roomId, SignalMessage message) {
      return message;
    }
    @PostMapping("/api/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody CreateRoomRequest req) {
      Room room = roomService.createRoom(req.getName(), req.getUserId());
      return ResponseEntity.ok(room);
    }
    
    @PostMapping("/api/rooms/{roomId}/join")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId, @RequestBody JoinRoomRequest req) {
      roomService.addUserToRoom(roomId, req.getUserId());
      return ResponseEntity.ok().build();
    }
    
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("video") MultipartFile file) {
        try {
            Path uploadDir = Paths.get("recordings");
            Files.createDirectories(uploadDir); // Ensure folder exists

            Path filePath = uploadDir.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());

            return ResponseEntity.ok("Video uploaded to " + filePath.toString());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to save file: " + e.getMessage());
        }
    }


}
