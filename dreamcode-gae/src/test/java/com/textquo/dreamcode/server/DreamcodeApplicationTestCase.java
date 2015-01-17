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

import com.jayway.restassured.response.ResponseBody;
import com.textquo.dreamcode.server.common.DreamcodeException;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.domain.User;
import com.textquo.dreamcode.server.domain.rest.CursorDTO;
import com.textquo.dreamcode.server.domain.rest.EntityDTO;
import com.textquo.dreamcode.server.guice.GuiceConfigModule;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResource;
import com.textquo.dreamcode.server.guice.SelfInjectingServerResourceModule;
import com.textquo.dreamcode.server.repository.DocumentRepository;
import com.textquo.dreamcode.server.repository.gae.GaeDocumentRepository;
import com.textquo.dreamcode.server.resources.*;
import com.textquo.dreamcode.server.resources.gae.*;
import com.textquo.dreamcode.server.services.DocumentService;
import com.textquo.dreamcode.server.services.util.ShardedCounter;
import com.textquo.dreamcode.server.services.ShardedCounterService;
import com.textquo.dreamcode.server.services.UserService;
import com.textquo.dreamcode.server.services.common.AccessNotAllowedException;
import com.textquo.dreamcode.server.services.common.DocumentException;
import com.textquo.dreamcode.server.services.gae.GaeDocumentService;
import com.textquo.dreamcode.server.services.gae.GaeUserService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.guice.api.annotation.GuiceConfiguration;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;

import java.io.File;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@RunWith(Arquillian.class)
@GuiceConfiguration(GuiceConfigModule.class)
@RunAsClient
public class DreamcodeApplicationTestCase {

    static final String STORE_URL = "http://localhost:8080/publicstore";

    /**
     * Deployment for the test
     *
     * @return web archive
     */
    @Deployment(name = "default")
    public static WebArchive getTestArchive() {
        File[] file = Maven.resolver().loadPomFromFile("../pom.xml")
                .importCompileAndRuntimeDependencies()
                .importTestDependencies()
                .resolve("org.restlet.gae:org.restlet:2.2.1",
                        "org.restlet.gae:org.restlet.ext.servlet:2.2.1",
                        "org.restlet.gae:org.restlet.ext.jackson:2.2.1",
                        "org.restlet.gae:org.restlet.ext.json:2.2.1",
                        "com.googlecode.json-simple:json-simple:1.1.1",
                        "com.textquo:twist:0.0.1-SNAPSHOT",
                        "com.squareup.dagger:dagger:1.2.2").withTransitivity().asFile();
        File[] sdkFile = Maven.resolver().loadPomFromFile("../pom.xml")
                .importDependencies(ScopeType.TEST)
                .resolve("com.google.appengine:appengine-api-1.0-sdk:1.9.17a",
                        "com.google.appengine:appengine-api-labs:1.9.17a",
                        "com.google.appengine:appengine-tools-sdk:1.9.17a").withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "simple.war")
                .addAsLibraries(file)
                .addAsLibraries(sdkFile)
                .addClass(org.restlet.ext.servlet.ServerServlet.class)
                .addClass(DreamcodeApplication.class)
                .addClass(CursorDTO.class)
                .addClass(EntityDTO.class)
                .addClass(Document.class)
                .addClass(User.class)
                .addClass(DataStoresResource.class)
                .addClass(RootServerResource.class)
                .addClass(StatusServerResource.class)
                .addClass(PingServerResource.class)
                .addClass(GaeDummyServerResource.class)
                .addClass(GaeLinkingServerResource.class)
                .addClass(GaeTokenServerResource.class)
                .addClass(GaeDataStoreServerResource.class)
                .addClass(GaeDataStoresServerResource.class)
                .addClass(SelfInjectingServerResource.class)
                .addClass(SelfInjectingServerResourceModule.class)
                .addClass(GuiceConfigModule.class)
                .addClass(BaseResource.class)
                .addClass(DataStoreResource.class)
                .addClass(UserResource.class)
                .addClass(UsersResource.class)
                .addClass(LinkingResource.class)
                .addClass(UserService.class)
                .addClass(GaeUserService.class)
                .addClass(ShardedCounter.class)
                .addClass(ShardedCounterService.class)
                .addClass(DocumentService.class)
                .addClass(GaeDocumentService.class)
                .addClass(DocumentRepository.class)
                .addClass(GaeDocumentRepository.class)
                .addClass(DreamcodeException.class)
                .addClass(DocumentException.class)
                .addClass(AccessNotAllowedException.class)
                .addClass(JSONHelper.class)
                .setWebXML("web.xml")
                .addAsWebInfResource("appengine-web.xml")
                .addAsWebInfResource("logging.properties");
    }

    @Test
    @OperateOnDeployment("default")
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        get("http://localhost:8080/rest/ping")
                .then()
                .assertThat()
                .body(containsString(PingServerResource.PONG));
    }

    @Test
    @OperateOnDeployment("default")
    public void shouldBeAbleToAddEntityAndReadIt() throws Exception {
        String content = "{ \"content\" : \"sample content\" }";

        ResponseBody r = expect()
                .body("_id", equalTo(1))
                .body("content", equalTo("sample content"))
                .statusCode(200)
                .given().contentType("application/json")
                .body(content)
                .when()
                .post("http://localhost:8080/rest/test")
                .getBody();

        System.out.println(r.prettyPrint());

    }

}
