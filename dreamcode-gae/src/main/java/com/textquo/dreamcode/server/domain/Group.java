package com.textquo.dreamcode.server.domain;

import com.textquo.twist.annotations.Entity;
import com.textquo.twist.annotations.Id;

@Entity
public class Group {

    @Id
    private String id;

    private String name;

    public Group(){};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
