package com.dev.demoapp.model;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("message")
    public String message;

    @Override
    public String toString() {
        return
        "ErrorResponse{" + "message = '" + message + '\'' +
                "}";
    }

}
