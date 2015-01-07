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

import com.textquo.dreamcode.server.services.ShardedCounterService;
import org.restlet.data.Status;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Resource which has only one representation.
 */
public class RootServerResource extends ServerResource {

    @Inject
    ShardedCounterService shardCounterService;

    @Get("json")
    public String represent() {
        shardCounterService = new ShardedCounterService();
        String client = getRequest().getClientInfo().getAddress();
        org.restlet.Request restletRequest = getRequest();
        HttpServletRequest servletRequest = ServletUtils.getRequest(restletRequest);
        String localName = servletRequest.getLocalName();
        String path = getRequest().getResourceRef().getHostIdentifier() +
                getRequest().getResourceRef().getPath();
        String resp = "Hello, world (from the cloud!), your client IP: " + client + " and domain is: " + localName + " path is: " + path;
        resp = resp + ", shard count=" + shardCounterService.getCount("test");
        setStatus(Status.SUCCESS_OK);
        return resp;
    }
}