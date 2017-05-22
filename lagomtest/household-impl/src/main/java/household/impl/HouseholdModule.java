package household.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import household.api.HouseholdService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class HouseholdModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(HouseholdService.class, HouseholdServiceImpl.class));
  }
}
