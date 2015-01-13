package com.textquo.dreamcode.server.domain.rest;

import com.textquo.dreamcode.server.domain.Document;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntityDTO extends LinkedHashMap {

    public static EntityDTO createFrom(Document document){
        EntityDTO entity = new EntityDTO();
        Long id = document.getId();
        entity.set_id(id);
        Iterator<Map.Entry> it = document.getFields().entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry = it.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            entity.put(key, val);
        }
        Map metadata = new LinkedHashMap();
        metadata.put("lmd", document.getModified());
        metadata.put("ect", document.getCreated());
        entity.put("_md", metadata);
        return entity;
    }

    public Long get_id() {
        return (Long) get("_id");
    }

    public void set_id(Long _id) {
        put("_id", _id);
    }

    @Override
    public String toString(){
        String result = "EntityDTO=";
        Iterator<Map.Entry> it = entrySet().iterator();
        while(it.hasNext()){
            Map.Entry entry = it.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            result += "[" + key + "," + val + "]";
        }
        return result;
    }

}
