package com.textquo.dreamcode.client;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.junit.client.GWTTestCase;
//import junit.framework.Assert;
import org.restlet.client.Request;
import org.restlet.client.Response;
import org.restlet.client.Uniform;
import org.restlet.client.data.MediaType;
import org.restlet.client.data.Status;
import org.restlet.client.resource.ClientResource;

import java.io.IOException;

/**
 * Run $mvn clean appengine:devserver
 * for the dreamcode-gae package before running these test!
 */
public class DreamcodeSmokeTest extends GWTTestCase {

    private static String URL_BASE = "http://localhost:8080";

    @Override
    public String getModuleName() {
        return "com.textquo.dreamcode.Dreamcode";
    }
    @Override
    protected void gwtSetUp() throws Exception {
    }
    @Override
    protected void gwtTearDown() throws Exception {
    }
    public void testRequestBuilder(){
        final String jsonObject = "{}";
        String type = "test";
        String id = "1";
        //String url = URL_BASE + Routes.DREAMCODE + Routes.COLLECTIONS + "?type=" + type + "&id=" + id;
        String url = URL_BASE;
        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
        try{
            builder.setHeader("content-type", "application/json");
            builder.sendRequest(jsonObject, new RequestCallback() {
                public void onResponseReceived(com.google.gwt.http.client.Request request, com.google.gwt.http.client.Response response) {
                    String jsonResponse = response.getText();
                    //assertEquals(com.google.gwt.http.client.Response.SC_OK, response.getStatusCode());
                    //assertNotNull(jsonResponse);
                }
                public void onError(com.google.gwt.http.client.Request request, Throwable throwable) {
                    //callback.failure(throwable);
                }
            });
        } catch (RequestException e){
            //callback.failure(new Throwable(e.getMessage()));
            e.printStackTrace();
        }
    }
    public void testCollections_Root_Get(){
        String type = "test";
        String url = URL_BASE + Routes.DREAMCODE  + "/" + type;
        final Status[] status = new Status[1];
        ClientResource resource = new ClientResource(url);
        resource.setOnResponse(new Uniform() {
            public void handle(Request request, Response response) {
                status[0] = response.getStatus();
                String body = null;
                try {
                    body = response.getEntity().getText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assertEquals(Status.SUCCESS_OK, status[0]);
                assertNotNull(body);
                assertTrue(!body.isEmpty());
            }
        });
        resource.get(MediaType.APPLICATION_JSON);
    }
}
