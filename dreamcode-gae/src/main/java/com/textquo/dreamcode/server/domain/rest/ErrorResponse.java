package com.textquo.dreamcode.server.domain.rest;

public class ErrorResponse extends DocumentResponse {
    public void setError(String message){
        put("error", message);
    }
}
