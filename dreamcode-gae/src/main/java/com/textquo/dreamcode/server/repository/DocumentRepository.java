package com.textquo.dreamcode.server.repository;

import com.textquo.dreamcode.server.domain.Document;

public interface DocumentRepository {
    public Document create(Document doc);
    public Document read(String type, String docId);
    public Document update(Document doc);
    public void delete(String docId);
}
