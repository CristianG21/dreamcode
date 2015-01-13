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
import com.textquo.dreamcode.server.resources.DataStoresResource;
import com.textquo.dreamcode.server.services.DocumentService;
import com.textquo.twist.types.Cursor;
import com.textquo.twist.types.ListResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.util.Series;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

import static com.textquo.twist.ObjectStoreService.store;

// PATH: /{collections}
public class GaeDataStoresServerResource extends SelfInjectingServerResource
    implements DataStoresResource {

    @Inject
    DocumentService service;

    private static final int DEFAULT_STORE_LIMIT = 10;

    private static final Logger LOG
            = Logger.getLogger(GaeDataStoresServerResource.class.getName());

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
    /**
     * Post one or more entities. Entity id's will be auto-generated
     *
     * @param entity
     * @return
     */
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
                        document.setKind(type);
                        store().put(document);
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
                }
            } else {
                setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            }
        }
        return response;
    };


    @Get("json")
    public List<EntityDTO> list(Representation entity){
        List<EntityDTO> response = null;
        Series<Header> responseHeaders = (Series<Header>)
                getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Series(Header.class);
            getResponseAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS,
                    responseHeaders);
        }
        responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));

        String type = (String) getRequest().getAttributes().get("collections");
        String ql = getQueryValue("ql");
        String cursor = getQueryValue("cursor");
        String prev = getQueryValue("prev");
        String uri = getRequest().getOriginalRef().toString();
        String limit = getQueryValue("limit");

        LOG.info("Query for type=" + type);
        LOG.info("Limit=" + limit);

        int resultLimit = DEFAULT_STORE_LIMIT;
        if(limit != null){
            resultLimit = Integer.valueOf(limit);
        }

        try {
            Cursor startCursor = new Cursor(cursor);

            ListResult<Document> result = store().find(Document.class, type)
                    .withCursor(startCursor)
                    .limit(resultLimit).asList();

            long count = 0;
            boolean hasNext;
            String nextCursor = result.getWebsafeCursor();
            List<Document> resultList = result.getList();
            if(!resultList.isEmpty()){
                for (Document doc : result.getList()){
                    if(doc != null){
                        LOG.info("Enity="+doc.toString());
                        if(response == null){
                            response = new LinkedList<>();
                        }
                        EntityDTO entityDTO = EntityDTO.createFrom(doc);
                        response.add(entityDTO);
                        count++;
                    }
                }
            }
            setStatus(Status.SUCCESS_OK);
        } catch (Exception e){
            e.printStackTrace();
            setStatus(Status.SERVER_ERROR_INTERNAL);
        }
        return response;
    }
}
