package com.textquo.dreamcode.server.repository.gae;

import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.repository.DocumentRepository;
import static com.textquo.twist.ObjectStoreService.store;

public class GaeDocumentRepository implements DocumentRepository {

    @Override
    public Document create(Document doc){
        Preconditions.checkNotNull(doc, "Cannot store null document");
        store().put(doc);
        return doc;
    }

    @Override
    public Document read(String type, String docId) {
        Preconditions.checkNotNull(docId, "Null document id");
        return store().get(Document.class, type, docId);
    }

    @Override
    public Document update(Document doc) {
        Preconditions.checkNotNull(doc, "Cannot store null document");
        store().put(doc);
        return doc;
    }

    @Override
    public void delete(String type, String docId) {
        Preconditions.checkNotNull(docId, "Null document id");
        store().delete(Document.class, type, docId);
    }

}
