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
package com.textquo.dreamcode.client.utils;

import com.google.gwt.junit.client.GWTTestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestJsonConverter extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.textquo.dreamcode.Dreamcode";
    }
    @Override
    protected void gwtSetUp() throws Exception {
    }
    @Override
    protected void gwtTearDown() throws Exception {
    }

    public void testEncode() {
        Map<String,Object> toEncode = new LinkedHashMap<String,Object>();
        toEncode.put("key1", null);
        toEncode.put("key2", true);
        toEncode.put("key3", 1);
        toEncode.put("key4", "2");
        String json = JsonConverter.encode(toEncode).toString();
        assertEquals("{\"key1\":null, \"key2\":true, \"key3\":1, \"key4\":\"2\"}", json);
    }

    public void testDecode() {
        Map<String,Object> decoded = JsonConverter.decode("{\"key1\":null, \"key2\":true, \"key3\":1, \"key4\":\"2\"}");
        assertNull(decoded.get("key1"));
        assertEquals(true, decoded.get("key2"));
        assertEquals(1.0, decoded.get("key3"));
        assertEquals("2", decoded.get("key4"));
    }
}
