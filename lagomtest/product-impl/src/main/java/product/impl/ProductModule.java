/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package de.ryznr.product.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import de.ryznr.product.api.ProductService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class ProductModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(ProductService.class, ProductServiceImpl.class));
  }
}
