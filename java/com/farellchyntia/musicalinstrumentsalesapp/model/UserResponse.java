package com.farellchyntia.musicalinstrumentsalesapp.model;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("success")
    private final boolean success;

    @SerializedName("message")
    private final String message;

    public UserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
