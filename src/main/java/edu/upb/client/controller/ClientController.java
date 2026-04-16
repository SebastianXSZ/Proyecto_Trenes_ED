package edu.upb.client.controller;

import edu.upb.client.model.ClientModel;
import edu.upb.client.view.ClientView;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

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
      view.showError("No se pudo conectar al servidor.");
      return;
    }

    // Cargar estaciones desde el servidor
    String[] stations = model.getStationNames();
    view.setStationNames(stations);

    // Configurar handler de compra
    view.setPurchaseHandler(this::handlePurchase);

    view.setVisible(true);
    view.showMessage("Conectado. Seleccione origen y destino.");
  }

  private void handlePurchase(SaleDTO dto) {
    try {
      Ticket ticket = model.purchaseTicket(dto);
      if (ticket != null) {
        view.displayTicket(ticket);
      } else {
        view.showError("No se pudo completar la compra. Verifique disponibilidad.");
      }
    } catch (Exception e) {
      view.showError("Error en la compra: " + e.getMessage());
    }
  }
}