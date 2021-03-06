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
package com.textquo.dreamcode.server.resources;

import com.textquo.dreamcode.server.domain.rest.EntityDTO;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.Map;

public interface DataStoreResource extends BaseResource {
    @Post("json")
    public EntityDTO add(Representation entity);
    @Put("json")
    public EntityDTO update(Representation entity);
    @Get("json")
    public EntityDTO find();
    @Delete("json")
    public void remove(Representation entity);
}

