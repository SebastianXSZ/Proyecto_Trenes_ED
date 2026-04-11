package edu.upb.server.factory;

import edu.upb.common.Environment;
import edu.upb.server.controller.ServerController;
import edu.upb.server.model.ServerModel;
import edu.upb.server.model.History;
import edu.upb.server.view.ServerView;

public class ServerFactory {

  private ServerFactory() {
  }

  public static ServerController create() {
    Environment env = Environment.getInstance();
    if (env == null) {
      throw new IllegalStateException("Failed to create Environment");
    }

    History history = new History();
    ServerModel model = new ServerModel(env.getIp(), env.getPort(), env.getServiceName());
    ServerView view = new ServerView("Server Control Panel", history);

    return new ServerController(model, view);
  }
}