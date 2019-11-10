package com.math_riddles.core.service;

/**
 * @author phuocns on 26/11/2018.
 */

public class ServiceException extends Exception {
    private String code;

    public ServiceException(String code, String message){
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
