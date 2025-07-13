package com.example.chat.service;

import java.util.List;

import com.example.chat.model.Room;

public interface RoomService {
    Room createRoom(String name, String creatorUserId);
    void addUserToRoom(String roomId, String userId);
    void removeUserFromRoom(String roomId, String userId);
    Room getRoomById(String roomId);
    List<Room> getRoomsByUser(String userId);
    List<String> getUsersInRoom(String roomId);
    boolean isUserInRoom(String roomId, String userId);
}

