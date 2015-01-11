package com.textquo.dreamcode.server.domain.rest;

public class ErrorDreamcodeResponse extends DocumentDreamcodeResponse {
    public void setError(String message){
        put("error", message);
    }
}
