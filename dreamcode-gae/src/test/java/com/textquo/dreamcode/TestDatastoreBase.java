package com.textquo.dreamcode;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestDatastoreBase {
    private final static LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        helper.setUp();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        helper.tearDown();
    }
}
