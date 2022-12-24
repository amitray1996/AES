package com.example.ashis.agricultureexpertsservice.Model;

public class ChatExpert {

    String expertsProfile;
    String sender;
    String receiver;
    String collection;
    String attachFile;
    String responseMessage;
    String send;

    public ChatExpert() {
    }

    public ChatExpert(String expertsProfile, String sender, String receiver, String collection, String attachFile, String responseMessage, String send) {
        this.expertsProfile = expertsProfile;
        this.sender = sender;
        this.receiver = receiver;
        this.collection = collection;
        this.attachFile = attachFile;
        this.responseMessage = responseMessage;
        this.send = send;
    }

    public String getExpertsProfile() {
        return expertsProfile;
    }

    public void setExpertsProfile(String expertsProfile) {
        this.expertsProfile = expertsProfile;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }
}
