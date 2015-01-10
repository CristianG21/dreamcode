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
import com.google.inject.Inject;
import com.textquo.dreamcode.server.JSONHelper;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.domain.rest.DocumentResponse;
import com.textquo.dreamcode.server.domain.rest.ErrorResponse;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import com.textquo.dreamcode.server.services.ShardedCounterService;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import static com.textquo.twist.ObjectStoreService.store;

// PATH: /{collections}
public class GlobalStoresServerResource extends SelfInjectingServerResource {

    @Inject
    ShardedCounterService shardCounterService;

    private static final Logger LOG
            = Logger.getLogger(GlobalStoresServerResource.class.getName());

    /**
     * Post one or more entities. Entity id's will be auto-generated
     *
     * @param entity
     * @return
     */
    @Post("json")
    public Map add(Representation entity) {
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
        // TODO - Simplify this
        if(type == null || type.isEmpty() || type.equals("null") || type.equals("NULL")) {
            response = new ErrorResponse();
            ((ErrorResponse) response).setError("Must provide type as query parameter");
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
                    Map<String,Object> dreamObject = JSONHelper.parseJson(jsonText);
                    if(dreamObject != null){
                        dreamObject.put("__key__", null); // auto-generate
                        dreamObject.put("__kind__", type);
                        Key key = store().put(dreamObject);
                        response.setId(String.valueOf(key.getId()));
                        response.setType(type);
                        setStatus(Status.SUCCESS_OK);
                    } else {
                        setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    setStatus(Status.SERVER_ERROR_INTERNAL);
                    return response;
                } catch (JSONException e) {
                    e.printStackTrace();
                    setStatus(Status.SERVER_ERROR_INTERNAL);
                    return response;
                }
            } else {
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return response;
    };


    @Get("json")
    public Map list(Representation entity){
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

        LOG.info("Query for type=" + type);

        try {
            Iterator<Document> it = store().find(Document.class, type).limit(100).now();
            int count = 0;
            while(it.hasNext()){
                Document doc = it.next();
                Long docId = doc.getId();
                String docType = doc.getKind();
                Map properties = doc.getFields();
                DocumentResponse newDoc = new DocumentResponse();
                newDoc.setId(String.valueOf(docId));
                newDoc.setType(docType);
                newDoc.put("properties", properties);
                response.put("item" + count, newDoc);
                count++;
            }
            response.put("count", count);
            response.put("has_next", false);
            setStatus(Status.SUCCESS_OK);
        } catch (Exception e){
            e.printStackTrace();
            setStatus(Status.SERVER_ERROR_INTERNAL);
        }

        return response;
    }
}
