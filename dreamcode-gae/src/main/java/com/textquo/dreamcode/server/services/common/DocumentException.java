package com.textquo.dreamcode.server.services.common;

import com.textquo.dreamcode.server.common.DreamcodeException;

public class DocumentException extends DreamcodeException {
    public DocumentException(){
        super();
    }
    public DocumentException(String message){
        super(message);
    }
}
