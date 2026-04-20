package edu.upb.client.controller;

import edu.upb.client.model.ClientModel;
import edu.upb.client.view.LoginView;
import edu.upb.client.view.PurchaseView;
import edu.upb.common.SaleDTO;
import edu.upb.model.Ticket;

public class ClientController {
  private ClientModel model;
  private LoginView loginView;
  private PurchaseView purchaseView;

  public ClientController(ClientModel model) {
    this.model = model;
  }

  public void init() {
    boolean connected = model.connect();
    if (!connected) {
      javax.swing.JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor.");
      return;
    }

    loginView = new LoginView(model);
    loginView.setOnLoginSuccess(username -> {
      loginView.dispose();
      showPurchaseView();
    });
    loginView.setVisible(true);
  }

  private void showPurchaseView() {
    purchaseView = new PurchaseView(model);
    purchaseView.setStationNames(model.getStationNames());
    purchaseView.setPurchaseHandler(this::handlePurchase);
    purchaseView.setVisible(true);
  }

  private void handlePurchase(SaleDTO dto) {
    try {
      Ticket ticket = model.purchaseTicket(dto);
      if (ticket != null) {
        purchaseView.showTicket(ticket);
      } else {
        javax.swing.JOptionPane.showMessageDialog(purchaseView, "Compra fallida. Verifique disponibilidad.");
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(purchaseView, "Error: " + e.getMessage());
    }
  }
}