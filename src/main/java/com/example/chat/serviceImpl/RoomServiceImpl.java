package com.example.chat.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.chat.model.Room;
import com.example.chat.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    // In-memory room storage (can be replaced with DB later)
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    @Override
    public Room createRoom(String name, String creatorUserId) {
        String roomId = UUID.randomUUID().toString();
        Room room = new Room();
        room.setRoomId(roomId);
        room.setName(name);
        room.getParticipantUserIds().add(creatorUserId);
        rooms.put(roomId, room);
        return room;
    }

    @Override
    public void addUserToRoom(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found: " + roomId);
        }
        room.getParticipantUserIds().add(userId);
    }

    @Override
    public void removeUserFromRoom(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room != null) {
            room.getParticipantUserIds().remove(userId);
        }
    }

    @Override
    public Room getRoomById(String roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found: " + roomId);
        }
        return room;
    }

    @Override
    public List<Room> getRoomsByUser(String userId) {
        return rooms.values().stream()
            .filter(room -> room.getParticipantUserIds().contains(userId))
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getUsersInRoom(String roomId) {
        Room room = rooms.get(roomId);
        if (room != null) {
            return new ArrayList<>(room.getParticipantUserIds());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isUserInRoom(String roomId, String userId) {
        Room room = rooms.get(roomId);
        return room != null && room.getParticipantUserIds().contains(userId);
    }
}

