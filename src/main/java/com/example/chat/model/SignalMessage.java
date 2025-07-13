package com.example.chat.model;

import lombok.Data;

@Data
public class SignalMessage {

	  private String roomId;
	  private String sender;
	  private String receiver;
	  private String type; // offer, answer, candidate
	  private String data;
}
