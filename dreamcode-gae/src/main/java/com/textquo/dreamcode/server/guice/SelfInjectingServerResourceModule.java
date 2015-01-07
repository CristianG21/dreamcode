package com.textquo.dreamcode.server.guice;


import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

/**
 * Install this module to arrange for SelfInjectingServerResource instances to
 * have their members injected (idempotently) by the doInit method (which is
 * called automatically after construction).
 * 
 * Incubator code. Not available in maven
 * 
 * @see https
 *      ://github.com/restlet/restlet-framework-java/blob/master/incubator/org
 *      .restlet
 *      .ext.guice/src/org/restlet/ext/guice/SelfInjectingServerResourceModule
 *      .java
 * @author Tembrel
 */
public class SelfInjectingServerResourceModule extends AbstractModule {

	@Override
	protected final void configure() {
		requestStaticInjection(SelfInjectingServerResource.class);
	}

	@Provides
	SelfInjectingServerResource.MembersInjector membersInjector(final Injector injector) {
		return new SelfInjectingServerResource.MembersInjector() {
			public void injectMembers(Object object) {
				injector.injectMembers(object);
			}
		};
	}
}
