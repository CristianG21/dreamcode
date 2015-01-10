package com.textquo.dreamcode.server.services.gae;

import com.google.appengine.repackaged.com.google.common.base.Preconditions;
import com.textquo.dreamcode.server.domain.Document;
import com.textquo.dreamcode.server.domain.User;
import com.textquo.dreamcode.server.repository.DocumentRepository;
import com.textquo.dreamcode.server.services.DocumentService;
import com.textquo.dreamcode.server.services.UserService;
import com.textquo.dreamcode.server.services.common.AccessNotAllowedException;
import com.textquo.dreamcode.server.services.common.DocumentException;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

public class GaeDocumentService implements DocumentService {

    @Inject
    DocumentRepository repository;

    @Inject
    UserService userService;

    public GaeDocumentService(){}

    @Override
    public Document createDocument(Document document) {
        Preconditions.checkNotNull(document, "Cannot create null document");
        User user = userService.getLoggedInUser();
        if(user != null){
            document.setUserId(user.getId());
        }
        repository.create(document);
        return document;
    }

    @Override
    public Document readDocument(String type, Long docId) throws DocumentException {
        Preconditions.checkNotNull(type, "String type is null");
        //Preconditions.checkArgument(type.isEmpty(), "String type is empty");
        Preconditions.checkNotNull(docId, "Document id is required");
        User user = userService.getLoggedInUser();
        Document document = repository.read(type, docId);
        if(user != null & document != null){
            if(!document.getUserId().equals(user.getId())){
                throw new AccessNotAllowedException(String.valueOf(docId));
            }
        }
        return document;
    }

    @Override
    public Collection<Document> readDocuments(String queryField, String queryValue) throws DocumentException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Collection<Document> readDocuments(Map<String, Object> query) throws DocumentException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void updateDocument(Document document) throws DocumentException {
        Preconditions.checkNotNull(document, "Cannot update null document");
        Preconditions.checkNotNull(document.getId(), "Cannot update, id is null");
        //Preconditions.checkArgument(document.getId().isEmpty(), "Cannot update, id is empty");
        User user = userService.getLoggedInUser();
        if(user != null){
            document.setUserId(user.getId());
        }
        repository.update(document);
    }

    @Override
    public void deleteDocument(String type, Long docId) throws DocumentException {
        Preconditions.checkNotNull(type, "String type is null");
        Preconditions.checkArgument(type.isEmpty(), "String type is empty");
        Preconditions.checkNotNull(docId, "Document id is required");
        Document document = repository.read(type, docId);
        User user = userService.getLoggedInUser();
        if(user != null){
            if(document.getUserId().equals(user.getId())){

            } else {
                throw new AccessNotAllowedException(String.valueOf(docId));
            }
        }
    }

    @Override
    public void deleteDocument(String type, Long... ids) throws DocumentException {
        throw new RuntimeException("Not yet implemented");
    }
}
