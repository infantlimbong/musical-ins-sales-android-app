package com.farellchyntia.musicalinstrumentsalesapp.model;

import java.util.Date;

public class OrderResponse {
    private int orderId;
    private int userId;
    private int productId;
    private String orderDate; // Using String to simplify date handling, consider changing to Date object if necessary

    // Constructor
    public OrderResponse(int orderId, int userId, int productId, String orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
