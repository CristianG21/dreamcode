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

import com.textquo.dreamcode.server.domain.rest.AppStatusResponse;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.Map;

/**
 * Resource which has only one representation.
 */
public class StatusServerResource extends SelfInjectingServerResource {
    @Get("json")
    public Map represent() {
        // TODO - Get name and version from properties file
        AppStatusResponse status = new AppStatusResponse();
        status.setName("Dreamcode Application");
        status.setVersion("1.0.0-SNAPSHOT");
        return status;
    }
}