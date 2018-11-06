/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.models;

/**
 *
 * @author hypertech
 */
public class ResponseJSON<T> {
	
    private boolean status;

    private String message;

    private T data;

    public ResponseJSON(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    public ResponseJSON(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

