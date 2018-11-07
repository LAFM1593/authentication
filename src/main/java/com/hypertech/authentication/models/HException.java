/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypertech.authentication.models;

import com.google.gson.Gson;

/**
 *
 * @author hypertech
 */
public class HException extends Exception {
    
    private ResponseJSON<Object> response;
    
    public HException(String message) {        
        response = new ResponseJSON(false, message);
    }
    
    public HException(String message, String error) {        
        response = new ResponseJSON(false, message, error);
    }

    public HException(String message, String error, Throwable cause) {
	super(error, cause);
        response = new ResponseJSON(false, message, error, new Gson().toJson(cause));
    }

    public ResponseJSON<Object> getResponse() {
        return response;
    }
    
}
