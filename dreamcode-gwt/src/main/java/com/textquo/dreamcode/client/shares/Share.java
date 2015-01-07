package com.textquo.dreamcode.client.shares;

import org.restlet.client.resource.ClientResource;

import java.util.ArrayList;
import java.util.Collection;

public class Share {

    private String id;
    private Collection<String> readAccess;
    private Collection<String> writeAccess;

    public Share(){
        readAccess = new ArrayList<>();
        writeAccess = new ArrayList<>();
    }

    /**
     * Grant public read access
     */
    public void grantReadAccess(){

    }

    /**
     * Grant public write access
     */
    public void grantWriteAccess(){

    }

    public void revokeReadAccess(){}
    public void revokeWriteAccess(){}

    public void grantReadAccess(String email){}
    public void revokeWriteAccess(String[] emails){}
    public void subscribe(){}
    public void ussubscribe(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
