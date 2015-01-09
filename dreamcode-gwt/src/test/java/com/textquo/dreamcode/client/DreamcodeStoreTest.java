package com.textquo.dreamcode.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.textquo.dreamcode.client.shares.Share;
import com.textquo.dreamcode.client.stores.Store;

//import javax.inject.Inject;

public class DreamcodeStoreTest extends GWTTestCase {

    private static String URL_BASE = "http://localhost:8080";

    //@Inject
    Dreamcode dreamCode;

    @Override
    public String getModuleName() {
        return "com.textquo.dreamcode.Dreamcode";
    }
    @Override
    protected void gwtSetUp() throws Exception {
    }
    @Override
    protected void gwtTearDown() throws Exception {
    }

    public void test_add_new(){
        // Add new object
        String type = "note";
        DreamObject attributes = new DreamObject("color", "red");
        dreamCode.store().add(type, attributes, new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_update_existing(){
        // Update existing object
        Store store = dreamCode.store();
        String type = "note";
        String id = "abc456";
        DreamObject update = new DreamObject("size", 2);
        store.update(type, id, update, new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_find_one(){
        String type = "note";
        String id = "abc456";
        dreamCode.store().find(type, id, new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_load_all(){
        // Load all objects
        dreamCode.store().findAll(new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_load_all_from_one_type(){
        // Load all objects from one type
        String type = "note";
        dreamCode.store().findAll(type, new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_remove_existing(){
        String type = "note";
        String id = "abc4567";
        dreamCode.store().remove(type, id, new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_events(){
        // Listen to store events
        // New doc created
        dreamCode.store().on("add", new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        // Existing doc updated
        dreamCode.store().on("update", new DreamcodeCallback() {
            @Override
            public void success(Object updated) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        // Doc removed
        dreamCode.store().on("remove", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        // Any of the events above
        dreamCode.store().on("change", new DreamcodeCallback() {
            @Override
            public void success(Object changedObject) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        // All listeners can be filtered by type
        dreamCode.store().on("add:note", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        // All listeners can be filtered by type
        dreamCode.store().on("add:note", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        dreamCode.store().on("update:note", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        dreamCode.store().on("remove:note", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        dreamCode.store().on("change:note", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        // ... and by type and id
        dreamCode.store().on("add:note:uuid123", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        dreamCode.store().on("update:note:uuid123", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        dreamCode.store().on("remove:note:uuid123", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
        dreamCode.store().on("change:note:uuid123", new DreamcodeCallback() {
            @Override
            public void success(Object removed) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });
    }

    public void test_Share(){

        final Share share = new Share();
        // Grant /  Revoke access
        dreamCode.share().add(share, new DreamcodeCallback() {
            @Override
            public void success(Object result) {
                share.grantReadAccess();
                share.grantWriteAccess();
                share.revokeReadAccess();
                share.revokeWriteAccess();
                share.grantReadAccess("joe@example.com");

                String[] emails = {"joe@example.com", "lisa@example.com"};
                share.revokeWriteAccess(emails);
            }

            @Override
            public void failure(Throwable e) {

            }
        });

        // Add all 'todo' objects to the share
        dreamCode.store().findAll("todo").shareAt(share.getId());

        // Remove a specific todo from the share
        dreamCode.store().find("todo", "123").unshareAt(share.getId());

        // Add a new share and add some of my objects to it in one step
        dreamCode.store().findAll("todo").share(new DreamcodeCallback() {
            @Override
            public void success(Object result) {

            }

            @Override
            public void failure(Throwable e) {

            }
        });

        // Remove objects from all share
        dreamCode.store().findAll("todo").unshare();

        // Remove share
        dreamCode.share().remove(share.getId());

        // Subscribe / Unsubscribe
        dreamCode.share("shareId").subscribe();
        dreamCode.share("shareId").ussubscribe();
    }

}
