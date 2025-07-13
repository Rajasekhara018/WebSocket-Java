package com.example.chat.model;

import java.util.List;

import lombok.Data;

@Data
public class Room {

	  private String roomId;
	  private String name;
	  private List<String> participantUserIds;
}
