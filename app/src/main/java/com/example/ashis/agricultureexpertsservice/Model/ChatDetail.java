package com.example.ashis.agricultureexpertsservice.Model;

public class ChatDetail {

    String chattime;
    String message;
    String message_type;
    String sender_image_URL;
    String sender_name;
    String messageImageUrl;

    public ChatDetail() {
    }

    public ChatDetail(String chattime, String message, String message_type, String sender_image_URL, String sender_name, String messageImageUrl) {
        this.chattime = chattime;
        this.message = message;
        this.message_type = message_type;
        this.sender_image_URL = sender_image_URL;
        this.sender_name = sender_name;
        this.messageImageUrl = messageImageUrl;
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

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getSender_image_URL() {
        return sender_image_URL;
    }

    public void setSender_image_URL(String sender_image_URL) {
        this.sender_image_URL = sender_image_URL;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getMessageImageUrl() {
        return messageImageUrl;
    }

    public void setMessageImageUrl(String messageImageUrl) {
        this.messageImageUrl = messageImageUrl;
    }

    @Override
    public String toString() {
        return "ChatDetail{" +
                "chattime='" + chattime + '\'' +
                ", message='" + message + '\'' +
                ", message_type='" + message_type + '\'' +
                ", sender_image_URL='" + sender_image_URL + '\'' +
                ", sender_name='" + sender_name + '\'' +
                ", messageImageUrl='" + messageImageUrl + '\'' +
                '}';
    }
}
