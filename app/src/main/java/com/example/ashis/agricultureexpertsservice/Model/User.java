package com.example.ashis.agricultureexpertsservice.Model;

public class User {

    String userId;
    String fullName;
    String imageUrl;

    public User() {
    }

    public User(String userId, String fullName, String imageUrl) {
        this.userId = userId;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
