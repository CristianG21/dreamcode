package com.textquo.dreamcode.server.repository;

import com.textquo.dreamcode.server.domain.Document;

import java.util.Map;

public interface DocumentRepository {
    public Document create(Document document);
    public Document read(String type, Long docId);
    public Document update(Document doc);
    public void delete(String type, String docId);
}
