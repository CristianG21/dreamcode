package com.textquo.dreamcode.server.domain;

import com.textquo.twist.annotations.Embedded;
import com.textquo.twist.annotations.Entity;
import com.textquo.twist.annotations.Id;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
public class User {
    @Id(prefix = "user")
    private String id;
    private String email;
    private String passwordHash;
    // e.g. '@twitter' or '@facebook'
    private String domain;
    // e.g. 'name', 'age' etc
    @Embedded
    private Map<String,String> parameterHash;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Map<String, String> getParameterHash() {
        return parameterHash;
    }

    public void setParameterHash(Map<String, String> parameterHash) {
        this.parameterHash = parameterHash;
    }

    public void addParameterHash(String param, String value){
        if(parameterHash == null){
            parameterHash = new LinkedHashMap<>();
        }
        parameterHash.put(param, value);
    }

    public String getParameterHash(String param){
        if(parameterHash == null){
            parameterHash = new LinkedHashMap<>();
        }
        return parameterHash.get(param);
    }
}
