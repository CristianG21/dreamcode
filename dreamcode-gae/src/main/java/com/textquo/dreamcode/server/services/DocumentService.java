package com.textquo.dreamcode.server.services;

import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.services.common.DocumentException;

import java.util.Collection;
import java.util.Map;

public interface DocumentService {
    public Document createDocument(Document document);
    public Document readDocument(String type, Long docId) throws DocumentException;
    public Collection<Document> readDocuments(String queryField, String queryValue) throws DocumentException;
    public Collection<Document> readDocuments(Map<String,Object> query) throws DocumentException;
    public void updateDocument(Document document) throws DocumentException;
    public void deleteDocument(String type, Long docId) throws DocumentException;
    public void deleteDocument(String type, Long...ids) throws DocumentException;
}
