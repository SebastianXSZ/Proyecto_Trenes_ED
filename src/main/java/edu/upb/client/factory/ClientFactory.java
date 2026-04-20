package edu.upb.client.factory;

import edu.upb.client.controller.ClientController;
import edu.upb.client.model.ClientModel;
import edu.upb.common.Environment;

public class ClientFactory {

  private ClientFactory() {
  }

  public static ClientController create() {
    Environment env = Environment.getInstance();
    if (env == null) throw new IllegalStateException("Environment is not initialized");
    ClientModel model = new ClientModel(env.getIp(), env.getPort(), env.getServiceName());
    return new ClientController(model);
  }
}