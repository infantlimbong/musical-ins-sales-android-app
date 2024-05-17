package com.farellchyntia.musicalinstrumentsalesapp.model;

public class ProductResponse {
    private boolean success;
    private String message;

    // Constructor
    public ProductResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getter and Setter for success
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter and Setter for message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
