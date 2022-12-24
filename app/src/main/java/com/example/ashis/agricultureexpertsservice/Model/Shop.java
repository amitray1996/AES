package com.example.ashis.agricultureexpertsservice.Model;

import java.io.Serializable;

public class Shop implements Serializable {

    String description;
    String imageUrl;
    String pricePerUnit;
    String title;
    String totalQuantity;
    String type;
    String productId;
    String uploadBy;

    public Shop() {
    }

    public Shop(String description, String imageUrl, String pricePerUnit, String title, String totalQuantity, String type, String productId, String uploadBy) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.pricePerUnit = pricePerUnit;
        this.title = title;
        this.totalQuantity = totalQuantity;
        this.type = type;
        this.productId = productId;
        this.uploadBy = uploadBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(String pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pricePerUnit='" + pricePerUnit + '\'' +
                ", title='" + title + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", type='" + type + '\'' +
                ", productId='" + productId + '\'' +
                ", uploadBy='" + uploadBy + '\'' +
                '}';
    }
}
