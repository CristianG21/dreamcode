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

import com.textquo.dreamcode.server.services.DatastoreService;

import static com.textquo.twist.ObjectStoreService.store;

public class GaeDatastoreService implements DatastoreService {
    @Override
    public void put(Object obj) {
        store().put(obj);
    }

    @Override
    public <T> T get(Long id) {
        T obj = null;
        obj = (T) store().get(obj.getClass(), id);
        return obj;
    }

    @Override
    public void update(Object obj) {
        store().put(obj);
    }

    @Override
    public <T> T delete(Long id) {
        T obj = null;
        store().delete(obj.getClass(), id);
        return null;
    }
}
