package com.textquo.dreamcode.client;

import java.util.LinkedHashMap;
import java.util.Map;

public class DreamObject {

    private Map<String, Object> attributes;

    public DreamObject(String attribute, Object value){
        if(attributes == null){
            attributes = new LinkedHashMap<>();
        }
        attributes.put(attribute, value);
    }
    public DreamObject(){}

    public DreamObject put(String attribute, Object value){
        if(attributes == null){
            attributes = new LinkedHashMap<>();
        }
        attributes.put(attribute, value);
        return this;
    }
}
