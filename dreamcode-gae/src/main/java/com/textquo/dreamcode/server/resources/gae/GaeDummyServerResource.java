package com.textquo.dreamcode.server.resources.gae;

import com.textquo.dreamcode.server.domain.rest.AppStatusDreamcodeResponse;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import org.restlet.resource.Get;

import java.util.Map;

/**
 * Dummy server resource during development phase
 */
public class GaeDummyServerResource extends SelfInjectingServerResource {
    @Get("json")
    public Map represent() {
        // TODO - Get name and version from properties file
        AppStatusDreamcodeResponse status = new AppStatusDreamcodeResponse();
        status.setName("Dreamcode Application");
        status.setVersion("1.0.0-SNAPSHOT");
        return status;
    }
}
