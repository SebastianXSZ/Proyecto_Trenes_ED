package edu.upb.client.factory;

import edu.upb.client.controller.ClientController;
import edu.upb.client.model.ClientModel;
import edu.upb.client.view.PurchaseView;
import edu.upb.common.Environment;

public class ClientFactory {

  private ClientFactory() {
  }

  public static ClientController create() {

    Environment env = null;

    try {
      env = Environment.getInstance();
    } catch (Exception e) {
      System.err.println("Failed to initialize Environment: " + e.getMessage());
    }

    if (env == null) {
      throw new IllegalStateException("Environment is not initialized");
    }

    ClientModel model = new ClientModel(env.getIp(), env.getPort(), env.getServiceName());

    PurchaseView view = new PurchaseView(model);

    return new ClientController(model, view);
  }
}