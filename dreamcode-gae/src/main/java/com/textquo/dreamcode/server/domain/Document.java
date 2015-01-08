package com.textquo.dreamcode.server.domain;

import com.textquo.twist.annotations.Entity;
import com.textquo.twist.annotations.Flat;
import com.textquo.twist.annotations.Id;
import com.textquo.twist.annotations.Kind;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class Document {

    @Id(prefix = "document")
    private String id;

    @Kind
    private String kind;

    @Flat
    private Map fields;

    public Document(){}

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setField(String name, Object value){
        if(fields == null){
            fields = new LinkedHashMap();
        }
        fields.put(name, value);
    }

    public Object getField(String name){
        if(fields == null){
            fields = new LinkedHashMap();
        }
        return fields.get(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
