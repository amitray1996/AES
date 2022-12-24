package com.example.ashis.agricultureexpertsservice.Model;

public class Reviews{

    String id;
    String comment;
    String uploadBy;
    String uploadByImageUrl;

    public Reviews() {
    }

    public Reviews(String id, String comment, String uploadBy, String uploadByImageUrl) {
        this.id = id;
        this.comment = comment;
        this.uploadBy = uploadBy;
        this.uploadByImageUrl = uploadByImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    public String getUploadByImageUrl() {
        return uploadByImageUrl;
    }

    public void setUploadByImageUrl(String uploadByImageUrl) {
        this.uploadByImageUrl = uploadByImageUrl;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", uploadBy='" + uploadBy + '\'' +
                ", uploadByImageUrl='" + uploadByImageUrl + '\'' +
                '}';
    }
}
