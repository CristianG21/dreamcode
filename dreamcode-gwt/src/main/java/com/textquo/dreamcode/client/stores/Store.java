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
package com.textquo.dreamcode.client.stores;

import com.textquo.dreamcode.client.DreamObject;
import com.textquo.dreamcode.client.DreamcodeCallback;
import com.textquo.dreamcode.client.Routes;
import com.textquo.dreamcode.client.utils.JsonHelper;
import org.restlet.client.Request;
import org.restlet.client.Response;
import org.restlet.client.Uniform;
import org.restlet.client.resource.ClientResource;
import no.eirikb.gwtchannelapi.client.Channel;

import java.util.Map;

public class Store {

    public Store(){
    }

    public void add(String type, String id, Object dreamObject, final DreamcodeCallback callback){

    }

    public void add(String type, Object dreamObject, final DreamcodeCallback callback){

    }

    public Find find(String type, String id){
        return new Find(this, type);
    }

    public Find find(String type, String id, DreamcodeCallback callback){
        return new Find(this, type);
    }

    public FindAll findAll(String type){
        return new FindAll(this, type);
    }

    public FindAll findAll(String type, DreamcodeCallback callback){
        return new FindAll(this, type);
    }

    public FindAll findAll(DreamcodeCallback callback){
        return new FindAll(this);
    }

    public void update(String type, String id, DreamObject update, DreamcodeCallback callback){}

    public void remove(String type, String id, DreamcodeCallback callback){}

    public void on(String event, DreamcodeCallback callback){
    }

}
