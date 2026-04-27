package edu.upb.server.controller;

import edu.upb.server.model.ServerModel;
import edu.upb.server.view.ServerView;

/**
 * Controlador del servidor en la arquitectura MVC.
 * Inicializa la vista del servidor y despliega el servicio RMI.
 *
 * @author Sebastian Alberto Pinto Torres
 * @version 1.0
 */
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
