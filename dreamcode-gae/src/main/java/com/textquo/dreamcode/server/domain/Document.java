package com.textquo.dreamcode.server.domain;

import com.textquo.twist.annotations.Entity;
import com.textquo.twist.annotations.Kind;

import java.io.Serializable;

@Entity
public class Document implements Serializable{
    @Kind
    String kind;

    public Document(){}
}
