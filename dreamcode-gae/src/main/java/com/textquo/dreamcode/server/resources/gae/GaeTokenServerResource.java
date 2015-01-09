package com.textquo.dreamcode.server.resources.gae;

import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import com.textquo.dreamcode.server.resources.TokenResource;
import org.restlet.resource.Get;

public class GaeTokenServerResource extends SelfInjectingServerResource
    implements TokenResource {
    @Get
    @Override
    public String userLogin() {
        return "This is userLogin method";
    }
    @Get("?loggedIn")
    @Override
    public boolean isUserLoggedIn(){
        return false;
    }
}
