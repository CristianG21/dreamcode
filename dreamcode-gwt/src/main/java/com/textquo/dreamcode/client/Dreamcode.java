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
package com.textquo.dreamcode.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.textquo.dreamcode.client.publicstores.GlobalStore;
import com.textquo.dreamcode.client.shares.Share;
import com.textquo.dreamcode.client.shares.Shares;
import com.textquo.dreamcode.client.stores.Store;

public class Dreamcode {

    private static String serverRoot = ""; // e.g. http://dreamcode.appspot.com

    public Dreamcode(){}
    public Dreamcode(String serverRoot){
        this.serverRoot = serverRoot;
    }

    public static String getServerRoot() {
        return serverRoot;
    }

    public void setServerRoot(String serverRoot){
        this.serverRoot = serverRoot;
    }

    public Store store(){
        // TODO: Return injected instance instead
        return new Store();
    }

    public GlobalStore globalStore() {
        // TODO: Return injected instance instead
        return new GlobalStore();
    }

    public Shares share(){
        return new Shares();
    }

    public Share share(String shareId){
        // TODO: Get share from STACK
        return new Share();
    }

}
