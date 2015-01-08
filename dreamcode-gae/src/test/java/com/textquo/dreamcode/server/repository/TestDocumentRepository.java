package com.textquo.dreamcode.server.repository;

import com.textquo.dreamcode.LocalDatastoreTest;
import com.textquo.dreamcode.TestDatastoreBase;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.guice.GuiceConfigModule;
import org.jboss.arquillian.guice.api.annotation.GuiceConfiguration;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import javax.inject.Inject;
import javax.print.Doc;

@RunWith(Arquillian.class)
@GuiceConfiguration(GuiceConfigModule.class)
@FixMethodOrder(MethodSorters.JVM)
public class TestDocumentRepository extends TestDatastoreBase {

    @Inject
    DocumentRepository repository;

    @Test
    public void test_createDocument(){
        Document doc = new Document();
        doc.setKind("Friend");
        doc.setField("name", "John Doe");
        repository.create(doc);
        assertNotNull(doc.getId());
        System.out.println(doc.getId());
    }

    @Test
    public void test_readDocument(){
        Document doc = new Document();
        doc.setId("sample1");
        doc.setKind("Friend");
        doc.setField("name", "John Doe");
        repository.create(doc);
        Document saved = repository.read("Friend", doc.getId());
        assertNotNull(saved);
        assertEquals("sample1", saved.getId());
        assertTrue(saved.getId().startsWith("sample"));
    }

    @Test
    public void test_updateDocument(){
        Document doc = new Document();
        doc.setKind("Friend");
        doc.setField("name", "John Doe");
        repository.create(doc);
        doc.setField("name", "Peter Doe");
        Document updated = repository.update(doc);
        assertNotNull(doc.getId());
        assertEquals("Peter Doe", updated.getField("name"));
    }
}
