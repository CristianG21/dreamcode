/**
 *
 * Copyright (c) 2014 Kerby Martino and others. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *     __                                         __
 * .--|  .----.-----.---.-.--------.----.-----.--|  .-----.
 * |  _  |   _|  -__|  _  |        |  __|  _  |  _  |  -__|
 * |_____|__| |_____|___._|__|__|__|____|_____|_____|_____|
 *
 */
package com.textquo.dreamcode.server.guice;

import com.google.inject.AbstractModule;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Scopes;
import com.google.inject.name.Names;
import com.textquo.dreamcode.server.repository.DocumentRepository;
import com.textquo.dreamcode.server.repository.gae.GaeDocumentRepository;
import com.textquo.dreamcode.server.services.DocumentService;
import com.textquo.dreamcode.server.services.ShardedCounterService;
import com.textquo.dreamcode.server.services.UserService;
import com.textquo.dreamcode.server.services.gae.GaeDocumentService;
import com.textquo.dreamcode.server.services.gae.GaeUserService;
import org.restlet.Context;

import javax.inject.Scope;

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
        bind(DocumentService.class).to(GaeDocumentService.class).in(Scopes.SINGLETON);
        bind(UserService.class).to(GaeUserService.class).in(Scopes.SINGLETON);
        bind(String.class).annotatedWith(Names.named("pong")).toInstance("pong!");
    }
}
