package com.textquo.dreamcode.server.guice;

import com.google.inject.AbstractModule;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.textquo.dreamcode.server.repository.DocumentRepository;
import com.textquo.dreamcode.server.repository.gae.GaeDocumentRepository;
import com.textquo.dreamcode.server.services.ShardedCounterService;
import org.restlet.Context;

public class GuiceConfigModule extends AbstractModule {

    private static final Logger log = Logger.getLogger(GuiceConfigModule.class.getName());
    private Context context;

    public GuiceConfigModule(){}

    public GuiceConfigModule(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void configure() {
        // Suppress Guice warning when on GAE
        // see https://code.google.com/p/google-guice/issues/detail?id=488
        Logger.getLogger("com.google.inject.internal.util").setLevel(Level.WARNING);
        bind(ShardedCounterService.class).in(Scopes.SINGLETON);
        bind(DocumentRepository.class).to(GaeDocumentRepository.class).in(Scopes.SINGLETON);
        bind(String.class).annotatedWith(Names.named("pong")).toInstance("pong!");
    }
}
