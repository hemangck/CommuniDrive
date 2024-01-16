package com.example.communidrive;

public class ChatMessage {

    private String username;
    private String message;
    private String name;

    public ChatMessage(String username, String message, String name) {
        this.username = username;
        this.message = message;
        this.name = name;
    }

    // Required default constructor for Firebase
    public ChatMessage() {
    }

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

