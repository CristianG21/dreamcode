package com.textquo.dreamcode.client.shares;

import com.textquo.dreamcode.client.DreamcodeCallback;
import com.textquo.dreamcode.client.Routes;
import org.restlet.client.Request;
import org.restlet.client.Response;
import org.restlet.client.Uniform;
import org.restlet.client.data.MediaType;
import org.restlet.client.resource.ClientResource;

public class Shares {

    private static final String SHARE_BASE_URL = Routes.DREAMCODE_API + Routes.SHARES_API;

    private ClientResource resource;

    public Shares(){
        resource = new ClientResource(SHARE_BASE_URL);
    }

    public void add(final Share share, DreamcodeCallback callback){
        String shareObject = "";
        resource.setOnResponse(new Uniform() {
            @Override
            public void handle(Request request, Response response) {
                String shareId = ""; // get from response
                share.setId(shareId);
            }
        });
        resource.post(shareObject, MediaType.APPLICATION_JSON);
    }

    public void remove(String shareId){

    }

    public void remove(String shareId, DreamcodeCallback callback){

    }

    public Share share(String shareId){
        return new Share();
    }
}
