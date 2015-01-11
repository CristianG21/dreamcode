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

import com.github.restdriver.serverdriver.http.response.Response;
import com.textquo.dreamcode.server.domain.rest.AppStatusDreamcodeResponse;
import com.textquo.dreamcode.server.domain.rest.DocumentDreamcodeResponse;
import com.textquo.dreamcode.server.domain.rest.DreamcodeResponse;
import com.textquo.dreamcode.server.domain.rest.ErrorDreamcodeResponse;
import com.textquo.dreamcode.server.guice.GuiceConfigModule;
import com.textquo.dreamcode.server.resources.gae.PingServerResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.guice.api.annotation.GuiceConfiguration;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static com.github.restdriver.serverdriver.RestServerDriver.*;
import static com.github.restdriver.serverdriver.Matchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
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
                // Domain Models
                .addClass(com.textquo.dreamcode.server.DreamcodeApplication.class)
                .addClass(com.textquo.dreamcode.server.domain.Document.class)
                .addClass(com.textquo.dreamcode.server.domain.User.class)
                .addClass(AppStatusDreamcodeResponse.class)
                .addClass(DocumentDreamcodeResponse.class)
                .addClass(ErrorDreamcodeResponse.class)
                .addClass(DreamcodeResponse.class)
                        // Resources
                .addClass(com.textquo.dreamcode.server.resources.gae.RootServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.StatusServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.PingServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GaeDummyServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GaeLinkingServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GaeTokenServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GlobalStoreServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GlobalStoresServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.BaseResource.class)
                .addClass(com.textquo.dreamcode.server.resources.GlobalStoreResource.class)
                .addClass(com.textquo.dreamcode.server.resources.UserResource.class)
                .addClass(com.textquo.dreamcode.server.resources.UsersResource.class)
                .addClass(com.textquo.dreamcode.server.resources.LinkingResource.class)
                // Services
                .addClass(com.textquo.dreamcode.server.services.UserService.class)
                .addClass(com.textquo.dreamcode.server.services.gae.GaeUserService.class)
                .addClass(com.textquo.dreamcode.server.services.ShardedCounter.class)
                .addClass(com.textquo.dreamcode.server.services.ShardedCounterService.class)
                .addClass(com.textquo.dreamcode.server.services.DocumentService.class)
                .addClass(com.textquo.dreamcode.server.services.gae.GaeDocumentService.class)
                // Repositories
                .addClass(com.textquo.dreamcode.server.repository.DocumentRepository.class)
                .addClass(com.textquo.dreamcode.server.repository.gae.GaeDocumentRepository.class)
                .addClass(com.textquo.dreamcode.server.guice.SelfInjectingServerResource.class)
                .addClass(com.textquo.dreamcode.server.guice.SelfInjectingServerResourceModule.class)
                .addClass(com.textquo.dreamcode.server.guice.GuiceConfigModule.class)
                        // Exceptions
                .addClass(com.textquo.dreamcode.server.common.DreamcodeException.class)
                .addClass(com.textquo.dreamcode.server.services.common.DocumentException.class)
                .addClass(com.textquo.dreamcode.server.services.common.AccessNotAllowedException.class)
                .addClass(com.textquo.dreamcode.server.JSONHelper.class)

                .setWebXML("web.xml")
                .addAsWebInfResource("appengine-web.xml")
                .addAsWebInfResource("logging.properties");
    }

    @Test
    @OperateOnDeployment("default")
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        String body = readAllAndClose(new URL("http://localhost:8080/ping").openStream());
        assertEquals(
                "Verify that the servlet was deployed and returns expected result",
                PingServerResource.PONG,
                body);
        Response response = get("http://localhost:8080/ping", body("", "application/json"));
        assertThat(response, hasStatusCode(200));
        assertThat(response, hasResponseBody(is(PingServerResource.PONG)));

    }

    @Test
    @OperateOnDeployment("default")
    public void shouldBeAbleToAddEntityAndReadIt() throws Exception {
        String toSave = "{ \"content\" : \"sample content\" }";
        Response postResponse = post("http://localhost:8080/test", body(toSave, "application/json"));
        Response getResponse = get("http://localhost:8080/test", body("", "application/json"));

        assertThat(postResponse, hasStatusCode(200));
        assertThat(getResponse, hasStatusCode(200));
        assertThat(getResponse.asJson(), hasJsonPath("count", equalTo(1)));
    }

    private String readAllAndClose(InputStream is) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int read;
            while ((read = is.read()) != -1) {
                out.write(read);
            }
        } finally {
            try {
                is.close();
            } catch (Exception ignored) {
            }
        }
        return out.toString();
    }
}
