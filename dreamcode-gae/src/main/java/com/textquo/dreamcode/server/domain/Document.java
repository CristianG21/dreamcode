/**
 *
 * Copyright (c) 2014 Kerby Martino and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *     __                                         __
 * .--|  .----.-----.---.-.--------.----.-----.--|  .-----.
 * |  _  |   _|  -__|  _  |        |  __|  _  |  _  |  -__|
 * |_____|__| |_____|___._|__|__|__|____|_____|_____|_____|
 *
 */
package com.textquo.dreamcode.server.domain;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.textquo.dreamcode.server.JSONHelper;
import com.textquo.twist.annotations.*;
import org.boon.Str;

import java.util.*;

@Entity
public class Document {

    @Id
    private Long id;

    @Kind
    private String kind;

    private String userId;

    @Embedded
    private List<String> readAccess;
    @Embedded
    private List<String> writeAccess;

    // Keys to view document
    @Embedded
    private List<String> readKeys;

    // Keys to modify/delete document
    @Embedded
    private List<String> writeKeys;

    // Overrides all read access
    private boolean isPublicRead = false;

    // Overrides all write access
    private boolean isPublicWrite = false;

    private Date created;

    private Date modified;

    @Flat
    private Map fields;

    public Document(){}

    public Document(String kind){}

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

    public Map getFields(){
        if(fields == null){
            fields = new LinkedHashMap();
        }
        return fields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getReadAccess() {
        return readAccess;
    }

    public void setReadAccess(List<String> readAccess) {
        this.readAccess = readAccess;
    }

    public List<String> getWriteAccess() {
        return writeAccess;
    }

    public void setWriteAccess(List<String> writeAccess) {
        this.writeAccess = writeAccess;
    }

    public boolean hasReadAccess(String userId){
        return readAccess.contains(userId) ? true : false;
    }

    public boolean hasWriteAccess(String userId){
        return writeAccess.contains(userId) ? true : false;
    }

    public boolean addReadAccess(String userId){
        return readAccess.add(userId);
    }

    public boolean addWriteAccess(String userId){
        return writeAccess.add(userId);
    }


    public boolean isPublicRead() {
        return isPublicRead;
    }

    public void setPublicRead(boolean isPublicRead) {
        this.isPublicRead = isPublicRead;
    }

    public boolean isPublicWrite() {
        return isPublicWrite;
    }

    public void setPublicWrite(boolean isPublicWrite) {
        this.isPublicWrite = isPublicWrite;
    }

    public List<String> getReadKeys() {
        return readKeys;
    }

    public void setReadKeys(List<String> readKeys) {
        this.readKeys = readKeys;
    }

    public List<String> getWriteKeys() {
        return writeKeys;
    }

    public void setWriteKeys(List<String> writeKeys) {
        this.writeKeys = writeKeys;
    }

    public boolean addReadKey(String keyHash){
        return readKeys.add(userId);
    }

    public boolean addWriteKey(String keyHash){
        return writeKeys.add(userId);
    }

    public static Document createFrom(String jsonText){
        Document entity = new Document();
        Map<String,Object> map = JSONHelper.parseJson(jsonText);
        Object id = map.get("_id");
        if(id != null){
            if(id instanceof Long){
                entity.setId((Long) id);
            }
        }
        Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,Object> entry = it.next();
            String key = entry.getKey();
            Object val = entry.getValue();
            entity.setField(key, val);
        }
        return entity;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
