package com.textquo.dreamcode.server.repository;

import com.textquo.dreamcode.TestDatastoreBase;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.guice.GuiceConfigModule;
import org.jboss.arquillian.guice.api.annotation.GuiceConfiguration;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import javax.inject.Inject;

@RunWith(Arquillian.class)
@GuiceConfiguration(GuiceConfigModule.class)
@FixMethodOrder(MethodSorters.JVM)
public class TestDocumentRepository extends TestDatastoreBase {

//    public static class Friend extends Document {
//        public Friend(){
//            super("Friend");
//        }
//    }

    @Inject
    DocumentRepository repository;

    @Test
    public void test_createDocument(){
        Document friend = new Document("Friend");
        friend.setField("name", "John Doe");
        repository.create(friend);
        assertNotNull(friend.getId());
        System.out.println(friend.getId());
    }

    @Test
    public void test_readDocument(){
        Document friend = new Document("Friend");
        friend.setField("name", "John Doe");
        friend.setId("sample1");
        friend.setKind("Friend");
        repository.create(friend);
        Document saved = repository.read("Friend", friend.getId());
        assertNotNull(saved);
        assertEquals("sample1", saved.getId());
        assertTrue(saved.getId().startsWith("sample"));
    }

    @Test
    public void test_updateDocument(){
        Document friend = new Document("Friend");
        friend.setField("name", "John Doe");
        friend.setField("name", "John Doe");
        repository.create(friend);
        friend.setField("name", "Peter Doe");
        Document updated = repository.update(friend);
        assertNotNull(friend.getId());
        assertEquals("Peter Doe", updated.getField("name"));
    }
}
