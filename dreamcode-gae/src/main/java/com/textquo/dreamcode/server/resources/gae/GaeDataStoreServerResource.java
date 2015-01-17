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

import com.google.inject.Inject;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.domain.rest.EntityDTO;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import com.textquo.dreamcode.server.resources.DataStoreResource;
import com.textquo.dreamcode.server.services.DocumentService;
import com.textquo.dreamcode.server.services.ShardedCounterService;
import com.textquo.twist.common.ObjectNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Post;
import org.restlet.util.Series;

import java.io.IOException;
import java.util.logging.Logger;

public class GaeDataStoreServerResource extends SelfInjectingServerResource
        implements DataStoreResource {

    private static final Logger LOG
            = Logger.getLogger(GaeDataStoreServerResource.class.getName());

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

    @Post("json")
    public EntityDTO add(Representation entity) {
        EntityDTO response = null;
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        String type = (String) getRequest().getAttributes().get("collections");
        String entityId = (String) getRequest().getAttributes().get("entity_id");
        // TODO - Simplify this
        if(type == null || type.isEmpty() || type.equals("null") || type.equals("NULL")) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } else {
            if(entity != null){
                LOG.info("Object type=" + type);
                JsonRepresentation represent = null;
                JSONObject jsonobject = null;
                try {
                    represent = new JsonRepresentation(entity);
                    jsonobject = represent.getJsonObject();
                    String jsonText = jsonobject.toString();
                    Document document = Document.createFrom(jsonText);
                    if(document != null){
                        if(entityId != null){
                            Long id = Long.valueOf(entityId);
                            document.setId(id);
                        }
                        document.setKind(type);
                        service.createDocument(document);
                        response = EntityDTO.createFrom(document);
                        setStatus(Status.SUCCESS_OK);
                    } else {
                        setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    setStatus(Status.SERVER_ERROR_INTERNAL);
                } catch (JSONException e) {
                    e.printStackTrace();
                    setStatus(Status.SERVER_ERROR_INTERNAL);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                }
            } else {
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return response;
    };

    @Override
    public EntityDTO update(Representation entity){
        return add(entity);
    }

    @Override
    public EntityDTO find(){
        EntityDTO response = null;
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
        try {
            if(type == null || type.isEmpty() || type.equals("null") || type.equals("NULL")) {
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            } else {
                LOG.info("Finding document type="+type);
                Document doc = service.readDocument(type, Long.valueOf(id));
                response = EntityDTO.createFrom(doc);
                setStatus(Status.SUCCESS_OK);
            }
        } catch (ObjectNotFoundException e){
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        } catch (Exception e){
            setStatus(Status.SERVER_ERROR_INTERNAL);
        }
        return response;
    };

    @Override
    public void remove(Representation entity){
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
        String type = (String) getRequest().getAttributes().get("collections");
        String entityId = (String) getRequest().getAttributes().get("entity_id");
        if(entityId != null && type != null && !entityId.isEmpty() && !type.isEmpty()){
            try {
                Long id = Long.valueOf(entityId);
                service.deleteDocument(type, id);
            } catch (NumberFormatException e){
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            } finally {
                setStatus(Status.SUCCESS_OK);
            }
        } else {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    };

}
