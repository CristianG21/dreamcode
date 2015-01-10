package com.textquo.dreamcode.server.services.common;

/**
 * Created by kmartino on 1/8/15.
 */
public class AccessNotAllowedException extends DocumentException {
    public AccessNotAllowedException(String documentId){
        super("Not allowed to access document " + documentId);
    }
}
