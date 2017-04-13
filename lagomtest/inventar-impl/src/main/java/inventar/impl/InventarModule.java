/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package inventar.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import inventar.api.InventarService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class InventarModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(InventarService.class, InventarServiceImpl.class));
  }
}
