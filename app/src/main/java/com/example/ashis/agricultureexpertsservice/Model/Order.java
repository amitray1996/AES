package com.example.ashis.agricultureexpertsservice.Model;

public class Order {
    String orderId;
    String productName;
    String productImageUrl;
    String orderDate;
    String totalAmount;
    String totalQuantity;
    String orderByName;
    String orderBy;
    String contactName;
    String contactNumber;

    public Order(String orderId, String productName, String productImageUrl, String orderDate, String totalAmount, String totalQuantity, String orderByName, String orderBy, String contactName, String contactNumber) {
        this.orderId = orderId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.totalQuantity = totalQuantity;
        this.orderByName = orderByName;
        this.orderBy = orderBy;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getOrderByName() {
        return orderByName;
    }

    public void setOrderByName(String orderByName) {
        this.orderByName = orderByName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
