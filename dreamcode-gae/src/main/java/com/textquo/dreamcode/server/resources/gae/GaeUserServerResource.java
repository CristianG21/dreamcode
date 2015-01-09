package com.textquo.dreamcode.server.resources.gae;

import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import com.textquo.dreamcode.server.resources.UserResource;
import org.restlet.representation.Representation;

public class GaeUserServerResource extends SelfInjectingServerResource implements UserResource {

    @Override
    public Representation addUser(Representation entity) {
        return null;
    }

    @Override
    public Representation updateUser(Representation entity) {
        return null;
    }

    @Override
    public Representation findUser(Representation entity) {
        return null;
    }

    @Override
    public Representation removeUser(Representation entity) {
        return null;
    }

    @Override
    public void doOptions(Representation entity) {

    }
}
