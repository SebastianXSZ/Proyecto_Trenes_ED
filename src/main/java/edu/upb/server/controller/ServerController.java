package edu.upb.server.controller;

import edu.upb.server.model.ServerModel;
import edu.upb.server.view.ServerView;

public class ServerController {

  ServerModel model;
  ServerView view;

  public ServerController(ServerModel model, ServerView view) {
    this.model = model;
    this.view = view;
  }

  public void init() {
    if (model.deploy()) {
      view.initComponents(event -> {
        view.startStatus("Server is already");
        return null;
      });
    } else {
      view.setMessage("Failed to deploy the server.");
    }
  }

}
