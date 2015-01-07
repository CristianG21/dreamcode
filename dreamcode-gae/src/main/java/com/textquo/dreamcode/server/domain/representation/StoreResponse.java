package com.textquo.dreamcode.server.domain.representation;

import java.util.LinkedHashMap;
import java.util.Map;

public class StoreResponse {

    private String id;

    private String type;

    public StoreResponse(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("_id", id);
        map.put("_type", type);
        return map;
    }
}
