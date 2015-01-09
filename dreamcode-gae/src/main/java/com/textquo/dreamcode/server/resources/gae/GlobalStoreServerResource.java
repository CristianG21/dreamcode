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
package com.textquo.dreamcode.server.resources.gae;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.log.LogService;
import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.textquo.dreamcode.server.JSONHelper;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.domain.rest.DocumentResponse;
import com.textquo.dreamcode.server.domain.rest.ErrorResponse;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import com.textquo.dreamcode.server.resources.GlobalStoreResource;
import com.textquo.dreamcode.server.services.DocumentService;
import com.textquo.dreamcode.server.services.ShardedCounterService;
import org.apache.commons.logging.Log;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.*;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.util.Series;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.textquo.twist.ObjectStoreService.store;

public class GlobalStoreServerResource extends SelfInjectingServerResource
        implements GlobalStoreResource {

    private static final Logger LOG
            = Logger.getLogger(GlobalStoreServerResource.class.getName());

    @Inject
    ShardedCounterService shardCounterService;

    @Inject
    DocumentService service;

    @Override
    public void doOptions(Representation entity) {
        // Add additional headers to response
        Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");
        if (responseHeaders == null) {
            responseHeaders = new Form();
            getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
        }
        responseHeaders.add("Access-Control-Allow-Origin", "*");
        responseHeaders.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
        responseHeaders.add("Access-Control-Allow-Credentials", "false");
        responseHeaders.add("Access-Control-Max-Age", "60");
    }

    @Override
    public Map add(Representation entity){
        DocumentResponse response = new DocumentResponse();
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));

        String id = String.valueOf(getQueryValue("id"));
        String type = String.valueOf(getQueryValue("type"));

        //Preconditions.checkNotNull(type, "Object type cannot be null");
        //Preconditions.checkNotNull(entity, "Object cannot be null");

        // TODO - Simplify this
        if(id == null || id.isEmpty() || id.equals("null") || id.equals("NULL")) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setError("Must provide id as query parameter");
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } else if(type == null || type.isEmpty() || type.equals("null") || type.equals("NULL")) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setError("Must provide type as query parameter");
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } else {
            if(entity != null){
                LOG.info("Object type=" + type);
                LOG.info("Object id=" + id);
                //String namespace = getQueryValue("namespace");
                try{
                    JsonRepresentation represent = new JsonRepresentation(entity);
                    JSONObject jsonobject = represent.getJsonObject();
                    String jsonText = jsonobject.toString();

                    if(id==null || id.isEmpty() || id.equals("null") || id.equals("NULL")){
                        shardCounterService = new ShardedCounterService();
                        shardCounterService.incrementCounter(type);
                        long count = shardCounterService.getCount(type);
                        LOG.info("Generated from sharded counter: " + count);
                        id = String.valueOf(count);
                    }
                    Map<String,Object> dreamObject = JSONHelper.parseJson(jsonText);
                    dreamObject.put("__key__", id);
                    dreamObject.put("__kind__", type);
                    Key key = store().put(dreamObject);

                    response.setId(key.getName());
                    response.setType(type);

                    setStatus(Status.SUCCESS_OK);
                } catch (ParseException e){
                    ((ErrorResponse) response).setError("Bad JSON Request object");
                    setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    return response;
                } catch (Exception e){
                    ((ErrorResponse) response).setError("Internal Server Error");
                    setStatus(Status.SERVER_ERROR_INTERNAL);
                    return response;
                } finally {

                }
            } else {
                response = new ErrorResponse();
                ((ErrorResponse) response).setError("Must provide JSON document to store");
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            }

        }
        return response;
    };

    @Override
    public Map update(Representation entity){
        DocumentResponse response = new DocumentResponse();
        return response;
    };

    @Override
    public Map find(){
        DocumentResponse response = new DocumentResponse();
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        String type = (String) getRequest().getAttributes().get("collections");
        String id = (String) getRequest().getAttributes().get("entity_id");
        if(type == null || type.isEmpty() || type.equals("null") || type.equals("NULL")) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setError("Must provide type as query parameter");
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } else {
            Document doc = service.readDocument(type, Long.valueOf(id));
            Long docId = doc.getId();
            String docType = doc.getKind();
            Map properties = doc.getFields();
            DocumentResponse newDoc = new DocumentResponse();
            newDoc.setId(String.valueOf(docId));
            newDoc.setType(docType);
            newDoc.put("properties", properties);
            response = newDoc;
            setStatus(Status.SUCCESS_OK);
        }
        return response;
    };

    @Override
    public Map remove(Representation entity){
        DocumentResponse response = new DocumentResponse();
        return response;
    };

}
