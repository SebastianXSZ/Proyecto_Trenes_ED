package edu.upb.client.controller;

import edu.upb.client.model.ClientModel;
import edu.upb.client.view.ClientView;

public class ClientController {
  private ClientModel model;
  private ClientView view;

  public ClientController(ClientModel model, ClientView view) {
    this.model = model;
    this.view = view;
  }

  public void init() {
    boolean connected = model.connect();
    if (!connected) {
      view.setMessage("Error: No se pudo conectar al servidor.");
      return;
    }
    view.setRegisterTicketHandler(text -> {
      model.register(text);
      return null;
    });
    view.setVisible(true);
    view.setMessage("Conectado al servidor. Ingrese un nombre.");
  }
}