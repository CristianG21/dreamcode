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
package com.textquo.dreamcode.server.services.gae;

import com.google.inject.Inject;
import com.textquo.dreamcode.server.customcode.CustomCode;
import com.textquo.dreamcode.server.customcode.CustomCodeCallback;
import com.textquo.dreamcode.server.services.CustomCodeService;
import com.textquo.dreamcode.server.services.DatastoreService;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kerby on 1/18/15.
 */
public class GaeCustomCodeService implements CustomCodeService {

    DatastoreService datastoreService = new GaeDatastoreService();

    Map<String, CustomCode> map = new LinkedHashMap<String, CustomCode>();

    @Override
    public void addCustomCode(String name, CustomCode customCode) {
        map.put(name, customCode);
    }

    @Override
    public void updateCustomCode(String name, CustomCode customCode) {
        map.put(name, customCode);
    }

    @Override
    public void runCustomCode(String name, Object data, CustomCodeCallback callback) {
        CustomCode customCode = map.get(name);
        customCode.run(data, datastoreService, callback);
    }

    @Override
    public void deleteCustomCode(String name) {

    }
}
