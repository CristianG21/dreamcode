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

import com.textquo.dreamcode.server.resources.gae.PingServerResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@RunWith(Arquillian.class)
@RunAsClient
public class DreamcodeApplicationTestCase {
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
                        "org.restlet.gae:org.restlet.ext.json:2.2.1").withTransitivity().asFile();
        File[] sdkFile = Maven.resolver().loadPomFromFile("../pom.xml")
                .importDependencies(ScopeType.TEST)
                .resolve("com.google.appengine:appengine-api-1.0-sdk:1.9.17a",
                        "com.google.appengine:appengine-api-labs:1.9.17a",
                        "com.google.appengine:appengine-tools-sdk:1.9.17a").withTransitivity().asFile();
        return ShrinkWrap.create(WebArchive.class, "simple.war")
                .addAsLibraries(file)
                .addAsLibraries(sdkFile)
                .addClass(org.restlet.ext.servlet.ServerServlet.class)
                .addClass(com.textquo.dreamcode.server.DreamcodeApplication.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.RootServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.PingServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GlobalStoreServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.gae.GlobalStoresServerResource.class)
                .addClass(com.textquo.dreamcode.server.resources.BaseResource.class)
                .addClass(com.textquo.dreamcode.server.resources.GlobalStoreResource.class)
                .addClass(com.textquo.dreamcode.server.resources.UserResource.class)
                .addClass(com.textquo.dreamcode.server.resources.UsersResource.class)
                .addClass(com.textquo.dreamcode.server.services.ShardedCounter.class)
                .addClass(com.textquo.dreamcode.server.services.ShardedCounterService.class)
                .setWebXML("web.xml")
                .addAsWebInfResource("appengine-web.xml")
                .addAsWebInfResource("logging.properties");
    }

    @Test
    @OperateOnDeployment("default")
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        String body = readAllAndClose(new URL("http://localhost:8080/ping").openStream());
        Assert.assertEquals(
                "Verify that the servlet was deployed and returns expected result",
                PingServerResource.PONG,
                body);
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
