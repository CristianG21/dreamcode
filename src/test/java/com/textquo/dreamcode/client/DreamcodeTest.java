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
