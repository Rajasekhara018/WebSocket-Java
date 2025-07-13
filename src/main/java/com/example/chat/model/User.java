package com.example.chat.model;

import lombok.Data;

@Data
public class User {

	  private String id;
	  private String username;
	  private String password; // securely hashed
}
