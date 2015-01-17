package com.textquo.dreamcode.server;

import com.google.inject.Inject;
import com.textquo.dreamcode.TestDatastoreBase;
import com.textquo.dreamcode.server.customcode.CustomCode;
import com.textquo.dreamcode.server.customcode.CustomCodeCallback;
import com.textquo.dreamcode.server.services.CustomCodeService;
import com.textquo.dreamcode.server.services.DatastoreService;
import com.textquo.dreamcode.server.services.gae.GaeCustomCodeService;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestCustomCode extends TestDatastoreBase {

    CustomCodeService customCodeService = new GaeCustomCodeService();

    public class MyCustomCode implements CustomCode {

        public MyCustomCode(){}

        @Override
        public void run(Object data, DatastoreService service, CustomCodeCallback callback) {
            Map sampleData = (Map) data;
            sampleData.put("__key__", "sample_only");
            sampleData.put("__kind__", "Sample");
            service.put(sampleData);
            if(sampleData != null){
                callback.success(sampleData);
            } else{
                callback.failure(sampleData, new RuntimeException("Something went wrong"));
            }
        }
    }

    @Test
    public void test(){
        MyCustomCode customCode = new MyCustomCode();
        customCodeService.addCustomCode("test", customCode);
        Map sampleData = new LinkedHashMap<>();
        customCodeService.runCustomCode("test", sampleData, new CustomCodeCallback() {
            @Override
            public void success(Object response) {

            }

            @Override
            public void failure(Object response, Throwable t) {

            }
        });
    }
}
