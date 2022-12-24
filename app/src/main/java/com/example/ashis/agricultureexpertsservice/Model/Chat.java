package com.example.ashis.agricultureexpertsservice.Model;

public class Chat {

    String chatId;
    String chattime;
    String message;
    String last_message;
    String last_message_time;
    String message_by;
    String message_by_image;

    public Chat() {
    }

    public Chat(String chatId, String chattime, String message, String last_message, String last_message_time, String message_by, String message_by_image) {
        this.chatId = chatId;
        this.chattime = chattime;
        this.message = message;
        this.last_message = last_message;
        this.last_message_time = last_message_time;
        this.message_by = message_by;
        this.message_by_image = message_by_image;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChattime() {
        return chattime;
    }

    public void setChattime(String chattime) {
        this.chattime = chattime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_message_time() {
        return last_message_time;
    }

    public void setLast_message_time(String last_message_time) {
        this.last_message_time = last_message_time;
    }

    public String getMessage_by() {
        return message_by;
    }

    public void setMessage_by(String message_by) {
        this.message_by = message_by;
    }

    public String getMessage_by_image() {
        return message_by_image;
    }

    public void setMessage_by_image(String message_by_image) {
        this.message_by_image = message_by_image;
    }
}

