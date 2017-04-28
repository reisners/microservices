package ${package}.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import ${lower-case-name}.api.${entity}Service;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class ${entity}Module extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(${entity}Service.class, ${entity}ServiceImpl.class));
  }
}
