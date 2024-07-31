package org.example.qtsv;

import org.springframework.data.domain.Page;

import java.util.List;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private  T data;


    public ApiResponse(boolean success, String message, T page) {
        this.success = success;
        this.message = message;
        this.data = page;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
