package com.example.chat.dto;

import java.util.List;

import lombok.Data;

@Data
public class CreateRoomRequest {

	  private String roomId;
	  private String name;
	  private String userId;
	  private List<String> participantUserIds;
}
