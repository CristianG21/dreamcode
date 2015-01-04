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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.gwt.RunAsGwtClient;
import org.jboss.arquillian.gwt.client.ArquillianGwtTestCase;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(Arquillian.class)
public class DreamcodeTest extends ArquillianGwtTestCase {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
//                .addClass(Greeter.class)
//                .addClass(GreetingService.class)
//                .addClass(GreetingServiceImpl.class)
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Test
    @RunAsGwtClient(moduleName = "com.textquo.dreamcode.Dreamcode")
    public void testAdd(){
    }
}
