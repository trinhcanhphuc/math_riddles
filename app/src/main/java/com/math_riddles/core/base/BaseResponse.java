package com.math_riddles.core.base;

/**
 * @author phuocns on 26/11/2018.
 */

public class BaseResponse<T> {
    protected boolean success;
    protected String message;
    protected T data;

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

