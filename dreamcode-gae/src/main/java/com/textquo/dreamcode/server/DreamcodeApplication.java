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
import com.textquo.dreamcode.server.resources.gae.*;
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
    router.attach(ROOT_URI, StatusServerResource.class);
    router.attach(ROOT_URI + "ping", PingServerResource.class);
    // Management
    router.attach(ROOT_URI + "management/token", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "management/users/{user_id}", GaeDummyServerResource.class); // Create admin user
    router.attach(ROOT_URI + "management/users/{user_id}/password", GaeDummyServerResource.class); // Set admin password
    router.attach(ROOT_URI + "management/users/resetpw", GaeDummyServerResource.class); // GET: Reset passwod, POST: Reset username & password
    router.attach(ROOT_URI + "management/users/{user_id}/activate?token={token}&confirm={confirm_email}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "management/users/{user_id}/reactivate", GaeDummyServerResource.class); // Reactivate admin user
    router.attach(ROOT_URI + "management/users/{user_id}/feed", GaeDummyServerResource.class);  // Get admin user feed
    // Activities
    router.attach(ROOT_URI + "users/{user_id}/activities", GaeDummyServerResource.class); // Create an activity
    router.attach(ROOT_URI + "groups/{group_id}/activities", GaeDummyServerResource.class); // Post activity to group
    // Groups
    router.attach(ROOT_URI + "groups", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "groups/group_name", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "groups/group_name/users/user_id", GaeDummyServerResource.class); // Add or Remove a user from group
    router.attach(ROOT_URI + "groups/group_name/feed", GaeDummyServerResource.class);
    // Roles
    router.attach(ROOT_URI + "roles", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "roles/{role_id}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "roles/{role_id}/permissions", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "roles/{role_id}/permissions?permission={grant_url_pattern", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "roles/{role_id}/users", GaeDummyServerResource.class); // Get users in Role
    router.attach(ROOT_URI + "roles/{role_id}/users/{user_id}", GaeDummyServerResource.class); // Add or Remove a user from role
    // Users
    router.attach(ROOT_URI + "users", GaeDummyServerResource.class); // Create a user
    router.attach(ROOT_URI + "users/{user_id}/password", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "users/{user_id}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "users?{query}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "users/{user_id}/feed", GaeDummyServerResource.class);
    // Shares
    router.attach(ROOT_URI + "shares", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "shares/{share_id}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "shares/{share_id}/accesses/{user_id}?access={access_type}", GaeDummyServerResource.class); // Access types: read, write, read_write
    router.attach(ROOT_URI + "shares/{share_id}/{entity_id}", GaeDummyServerResource.class); // Add remove
    // Collections
    router.attach(ROOT_URI + "{collections}", GlobalStoresServerResource.class);
    router.attach(ROOT_URI + "{collections}/{entity_id}", GlobalStoreServerResource.class);
    router.attach(ROOT_URI + "{collections}/{entity_id}?write_access_key=?{write_access_key}", GlobalStoreServerResource.class);
    router.attach(ROOT_URI + "{collections}?{query}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "{collections}/{entity_id}/{relationship}?{query}", GaeDummyServerResource.class);
    router.attach(ROOT_URI + "{collections}/{first_entity_id}/{relationship}/{second_entity_id}", GaeLinkingServerResource.class);

//    guard.setNext(router);

//    return guard;
    return router;
  }

  private Map<String,String> dreamcodeProperties(){
    Map<String,String> map = new LinkedHashMap<String, String>();
    InputStream is =  getContext().getClass().getResourceAsStream("/dreamcode.properties");
    Properties props = new Properties();
    try {
      props.load(is);
      map = new LinkedHashMap<String, String>((Map) props);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return map;
  }
}