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
package com.textquo.dreamcode.server;

import com.google.inject.Guice;
import com.textquo.dreamcode.server.guice.GuiceConfigModule;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResourceModule;
import com.textquo.dreamcode.server.resources.gae.GlobalStoreServerResource;
import com.textquo.dreamcode.server.resources.gae.GlobalStoresServerResource;
import com.textquo.dreamcode.server.resources.gae.PingServerResource;
import com.textquo.dreamcode.server.resources.gae.RootServerResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.security.MapVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class DreamcodeApplication extends Application {
    private static final String ROOT_URI = "/";

  /**
   * Creates a root Restlet that will receive all incoming calls.
   */
  @Override
  public Restlet createInboundRoot() {


    Guice.createInjector(new GuiceConfigModule(this.getContext()),
            new SelfInjectingServerResourceModule());

    // Create a simple password verifier
//    MapVerifier verifier = new MapVerifier();
//    verifier.getLocalSecrets().put(
//            dreamcodeProperties().get("admin.user"),
//            dreamcodeProperties().get("admin.pass").toCharArray());

    // Create a guard
    //ChallengeAuthenticator guard = new ChallengeAuthenticator(
    //getContext(), ChallengeScheme.HTTP_BASIC, "A custom challenge authentication scheme.");
    //guard.setVerifier(verifier);

    Router router = new Router(getContext());
    router.attach(ROOT_URI, RootServerResource.class);
    router.attach(ROOT_URI + "ping", PingServerResource.class);
    router.attach(ROOT_URI + "publicstore", GlobalStoreServerResource.class);
    router.attach(ROOT_URI + "publicstores", GlobalStoresServerResource.class);
//    router.attach(ROOT_URI + "user/{id}", UserServerResource.class);
//    router.attach(ROOT_URI + "share/{id}", ShareServerResource.class);
//    router.attach(ROOT_URI + "share/{id}/users", ShareUserServerResource.class);
//    router.attach(ROOT_URI + "share/{id}/stores", ShareStoreServerResource.class);
//    router.attach(ROOT_URI + "store/{id}", ShareStoreServerResource.class);
//    router.attach(ROOT_URI + "task/{id}", TaskServerResource.class);
//    guard.setNext(router);

//    return guard;
    return router;
  }

//  private Map<String,String> dreamcodeProperties(){
//    Map<String,String> map = new LinkedHashMap<String, String>();
//    InputStream is =  getContext().getClass().getResourceAsStream("/dreamcode.properties");
//    Properties props = new Properties();
//    try {
//      props.load(is);
//      map = new LinkedHashMap<String, String>((Map) props);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return map;
//  }
}