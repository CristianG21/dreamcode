package com.textquo.dreamcode.server.resources.gae;

import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import org.restlet.resource.Get;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dummy server resource during development phase
 */
public class GaeDummyServerResource extends SelfInjectingServerResource {
    @Get("json")
    public Map represent() {
        // TODO - Get name and version from properties file
        Map<String,Object> status = new LinkedHashMap<>();
        status.put("name", "Dreamcode Application");
        status.put("version", "1.0.0-SNAPSHOT");
        return status;
    }
}
